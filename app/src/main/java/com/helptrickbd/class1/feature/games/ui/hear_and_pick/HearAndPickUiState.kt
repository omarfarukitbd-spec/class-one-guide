package com.helptrickbd.class1.feature.games.ui.hear_and_pick

import androidx.compose.runtime.Immutable
import com.helptrickbd.class1.feature.drawing.domain.model.CelebrationState
import com.helptrickbd.class1.feature.games.domain.model.QuizQuestion

@Immutable
data class HearAndPickUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isCorrect: Boolean? = null,
    val isSpeaking: Boolean = false,
    val celebrationState: CelebrationState = CelebrationState(isCelebrating = false),
    val isGameOver: Boolean = false
) {
    val currentQuestion: QuizQuestion?
        get() = questions.getOrNull(currentIndex)
}
