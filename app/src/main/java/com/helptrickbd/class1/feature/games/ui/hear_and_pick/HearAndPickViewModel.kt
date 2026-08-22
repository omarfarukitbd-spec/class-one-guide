package com.helptrickbd.class1.feature.games.ui.hear_and_pick

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.core.audio.SoundFxHelper
import com.helptrickbd.class1.core.audio.StudioAudioEngine
import com.helptrickbd.class1.feature.drawing.domain.model.CelebrationState
import com.helptrickbd.class1.feature.games.data.datasource.QuizDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HearAndPickViewModel @Inject constructor(
    private val studioAudioEngine: StudioAudioEngine,
    private val soundFxHelper: SoundFxHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(HearAndPickUiState())
    val uiState: StateFlow<HearAndPickUiState> = _uiState.asStateFlow()

    init {
        startNewGame()
        viewModelScope.launch {
            studioAudioEngine.isSpeaking.collect { speaking ->
                _uiState.value = _uiState.value.copy(isSpeaking = speaking)
            }
        }
    }

    fun startNewGame() {
        val questions = QuizDataSource.generateRandomQuestions(8)
        _uiState.value = HearAndPickUiState(
            questions = questions,
            currentIndex = 0,
            score = 0,
            isGameOver = false
        )
        playCurrentPrompt()
    }

    fun playCurrentPrompt() {
        val q = _uiState.value.currentQuestion ?: return
        viewModelScope.launch {
            studioAudioEngine.playDirectAsset(q.audioPromptPath) {
                if (q.rhymePromptPath.isNotBlank()) {
                    viewModelScope.launch {
                        studioAudioEngine.playDirectAsset(q.rhymePromptPath)
                    }
                }
            }
        }
    }

    fun selectOption(optionIndex: Int) {
        val current = _uiState.value.currentQuestion ?: return
        if (_uiState.value.isCorrect == true) return // Prevent duplicate taps

        val isRight = optionIndex == current.correctIndex
        _uiState.value = _uiState.value.copy(
            selectedOptionIndex = optionIndex,
            isCorrect = isRight
        )

        viewModelScope.launch {
            if (isRight) {
                val newScore = _uiState.value.score + 1
                soundFxHelper.playVictoryChime()
                studioAudioEngine.playPraiseAudio(isEnglish = false)
                _uiState.value = _uiState.value.copy(
                    score = newScore,
                    celebrationState = CelebrationState(
                        isCelebrating = true,
                        starsEarned = 3,
                        praiseMessage = "সাবাশ! চমৎকার হয়েছে!"
                    )
                )
                delay(1800)
                _uiState.value = _uiState.value.copy(
                    celebrationState = CelebrationState(isCelebrating = false)
                )
                advanceQuestion()
            } else {
                soundFxHelper.playBubbleClick()
                delay(800)
                _uiState.value = _uiState.value.copy(
                    selectedOptionIndex = null,
                    isCorrect = null
                )
            }
        }
    }

    private fun advanceQuestion() {
        val nextIdx = _uiState.value.currentIndex + 1
        if (nextIdx < _uiState.value.questions.size) {
            _uiState.value = _uiState.value.copy(
                currentIndex = nextIdx,
                selectedOptionIndex = null,
                isCorrect = null
            )
            playCurrentPrompt()
        } else {
            _uiState.value = _uiState.value.copy(isGameOver = true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        studioAudioEngine.stop()
    }
}
