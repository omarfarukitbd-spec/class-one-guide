package com.helptrickbd.class1.feature.learn_hub.ui.quiz

import androidx.compose.runtime.Immutable
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizGameResult
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizMode
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizQuestion

@Immutable
sealed interface QuizUiState {
    @Immutable
    data object Loading : QuizUiState

    @Immutable
    data class ModeSelection(
        val totalStars: Int = 0
    ) : QuizUiState

    @Immutable
    data class Playing(
        val mode: QuizMode,
        val questions: List<QuizQuestion>,
        val currentIndex: Int = 0,
        val selectedOption: String? = null,
        val isAnswered: Boolean = false,
        val isCorrect: Boolean = false,
        val comboStreak: Int = 0,
        val score: Int = 0,
        val starsEarned: Int = 0,
        val showConfetti: Boolean = false,
        val isMuted: Boolean = false
    ) : QuizUiState {
        val currentQuestion: QuizQuestion get() = questions[currentIndex]
        val totalQuestions: Int get() = questions.size
        val progress: Float get() = if (totalQuestions > 0) (currentIndex + 1).toFloat() / totalQuestions else 0f
    }

    @Immutable
    data class GameOver(
        val result: QuizGameResult
    ) : QuizUiState
}
