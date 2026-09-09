package com.helptrickbd.class1.feature.learn_hub.ui.quiz

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.feature.learn_hub.domain.audio.QuizSoundManager
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizGameResult
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizMode
import com.helptrickbd.class1.feature.learn_hub.domain.usecase.quiz.GetQuizQuestionsUseCase
import com.helptrickbd.class1.feature.learn_hub.domain.usecase.quiz.GetQuizStatsUseCase
import com.helptrickbd.class1.feature.learn_hub.domain.usecase.quiz.SaveQuizResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizQuestionsUseCase: GetQuizQuestionsUseCase,
    private val saveQuizResultUseCase: SaveQuizResultUseCase,
    private val getQuizStatsUseCase: GetQuizStatsUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val soundManager = QuizSoundManager(context)

    private val _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        observeTotalStars()
    }

    private fun observeTotalStars() {
        viewModelScope.launch {
            getQuizStatsUseCase.getTotalStars().collectLatest { stars ->
                if (_uiState.value is QuizUiState.ModeSelection || _uiState.value is QuizUiState.Loading) {
                    _uiState.value = QuizUiState.ModeSelection(totalStars = stars ?: 0)
                }
            }
        }
    }

    fun selectMode(mode: QuizMode) {
        viewModelScope.launch {
            _uiState.value = QuizUiState.Loading
            val questions = getQuizQuestionsUseCase(mode)
            if (questions.isNotEmpty()) {
                val playing = QuizUiState.Playing(
                    mode = mode,
                    questions = questions,
                    isMuted = soundManager.isMuted.value
                )
                _uiState.value = playing
                soundManager.playVoicePrompt(playing.currentQuestion.promptVoiceAsset)
            } else {
                returnToModeSelection()
            }
        }
    }

    fun onOptionSelected(option: String) {
        val current = _uiState.value as? QuizUiState.Playing ?: return
        if (current.isAnswered) return

        val isCorrect = option == current.currentQuestion.targetLetter
        val newCombo = if (isCorrect) current.comboStreak + 1 else 0
        val starsGained = if (isCorrect) 10 + (newCombo * 2) else 0
        val scoreGained = if (isCorrect) 100 + (newCombo * 20) else 0

        _uiState.value = current.copy(
            selectedOption = option,
            isAnswered = true,
            isCorrect = isCorrect,
            comboStreak = newCombo,
            score = current.score + scoreGained,
            starsEarned = current.starsEarned + starsGained,
            showConfetti = isCorrect
        )

        if (isCorrect) {
            soundManager.playCorrectSound()
            if (newCombo >= 3) {
                viewModelScope.launch {
                    delay(300)
                    soundManager.playComboCheer()
                }
            }
        } else {
            soundManager.playWrongSound()
        }

        viewModelScope.launch {
            delay(1400)
            advanceToNextQuestion()
        }
    }

    private fun advanceToNextQuestion() {
        val current = _uiState.value as? QuizUiState.Playing ?: return
        val nextIndex = current.currentIndex + 1

        if (nextIndex < current.totalQuestions) {
            val nextState = current.copy(
                currentIndex = nextIndex,
                selectedOption = null,
                isAnswered = false,
                isCorrect = false,
                showConfetti = false
            )
            _uiState.value = nextState
            soundManager.playVoicePrompt(nextState.currentQuestion.promptVoiceAsset)
        } else {
            finishGame(current)
        }
    }

    private fun finishGame(current: QuizUiState.Playing) {
        viewModelScope.launch {
            val correctCount = current.score / 100 // approximation or tracker
            val accuracy = if (current.totalQuestions > 0) {
                ((current.score / 100).toFloat() / current.totalQuestions * 100).toInt().coerceIn(0, 100)
            } else 0

            val result = QuizGameResult(
                mode = current.mode,
                totalQuestions = current.totalQuestions,
                correctCount = correctCount.coerceAtMost(current.totalQuestions),
                score = current.score,
                starsEarned = current.starsEarned,
                accuracyPercent = accuracy
            )

            saveQuizResultUseCase(result)
            soundManager.playClappingCelebration()
            _uiState.value = QuizUiState.GameOver(result)
        }
    }

    fun replayQuestionVoice() {
        val current = _uiState.value as? QuizUiState.Playing ?: return
        soundManager.playVoicePrompt(current.currentQuestion.promptVoiceAsset)
    }

    fun toggleMute() {
        soundManager.toggleMute()
        val current = _uiState.value as? QuizUiState.Playing
        if (current != null) {
            _uiState.value = current.copy(isMuted = soundManager.isMuted.value)
        }
    }

    fun playAgain() {
        val lastMode = (_uiState.value as? QuizUiState.GameOver)?.result?.mode
            ?: (_uiState.value as? QuizUiState.Playing)?.mode
            ?: QuizMode.SOUND_TO_LETTER
        selectMode(lastMode)
    }

    fun returnToModeSelection() {
        soundManager.stopVoicePrompt()
        _uiState.value = QuizUiState.Loading
        observeTotalStars()
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
    }
}
