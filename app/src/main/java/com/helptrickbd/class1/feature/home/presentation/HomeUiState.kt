package com.helptrickbd.class1.feature.home.presentation

import androidx.compose.runtime.Immutable
import com.helptrickbd.class1.core.settings.domain.model.StorageInfo
import com.helptrickbd.class1.core.settings.domain.model.ThemeMode
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.SearchResult

/**
 * Immutable UI State for the Home screen including Storage & Theme settings.
 */
@Immutable
sealed interface HomeUiState {
    data object Loading : HomeUiState

    @Immutable
    data class Success(
        val userName: String,
        val selectedCurriculum: Curriculum,
        val resumeBook: Book?,
        val books: List<Book>,
        val storageInfo: StorageInfo = StorageInfo(),
        val themeMode: ThemeMode = ThemeMode.SYSTEM,
        val searchQuery: String = "",
        val searchResults: List<SearchResult> = emptyList()
    ) : HomeUiState

    data class Error(val message: String) : HomeUiState
}
