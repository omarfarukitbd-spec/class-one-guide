package com.helptrickbd.class1.feature.home.presentation

import androidx.compose.runtime.Immutable
import com.helptrickbd.class1.core.util.UiText
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.SearchResult

/**
 * Immutable UI State for the Home screen including Storage & Theme settings.
 */
@Immutable
sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Empty(val message: UiText) : HomeUiState

    @Immutable
    data class Success(
        val userName: String,
        val selectedCurriculum: Curriculum,
        val resumeBook: Book?,
        val books: List<Book>,
        val searchQuery: String = "",
        val searchResults: List<SearchResult> = emptyList(),
        val cloudNotice: String? = null
    ) : HomeUiState

    data class Error(val message: UiText) : HomeUiState
}
