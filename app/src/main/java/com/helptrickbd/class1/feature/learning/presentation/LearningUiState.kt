package com.helptrickbd.class1.feature.learning.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.helptrickbd.class1.feature.learning.domain.model.DrawingPath
import com.helptrickbd.class1.feature.learning.domain.model.LearningCategory
import com.helptrickbd.class1.feature.learning.domain.model.LearningItem

@Immutable
sealed interface LearningUiState {
    data object Loading : LearningUiState
    
    data class Success(
        val categories: List<LearningCategory>,
        val selectedCategory: LearningCategory,
        val items: List<LearningItem>,
        val selectedItem: LearningItem?,
        val paths: List<DrawingPath> = emptyList(),
        val selectedColor: Color = Color.Black,
        val isEraser: Boolean = false,
        val isSpeaking: Boolean = false,
        // Quiz State
        val quizOptions: List<LearningItem> = emptyList(),
        val correctOption: LearningItem? = null,
        val score: Int = 0
    ) : LearningUiState
    
    data class Error(val message: String) : LearningUiState
}
