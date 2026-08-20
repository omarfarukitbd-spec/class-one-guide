package com.helptrickbd.class1.feature.home.presentation

import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum

/**
 * UI State for the Home screen.
 */
sealed interface HomeUiState {
    data object Loading : HomeUiState
    
    data class Success(
        val userName: String,
        val selectedCurriculum: Curriculum,
        val resumeBook: Book?,
        val books: List<Book>
    ) : HomeUiState
    
    data class Error(val message: String) : HomeUiState
}
