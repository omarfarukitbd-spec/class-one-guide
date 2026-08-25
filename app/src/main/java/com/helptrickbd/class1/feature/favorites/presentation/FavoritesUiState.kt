package com.helptrickbd.class1.feature.favorites.presentation

import androidx.compose.runtime.Immutable
import com.helptrickbd.class1.feature.home.domain.model.Book

@Immutable
sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data class Success(
        val favoriteBooks: List<Book>,
        val recentBook: Book? = null
    ) : FavoritesUiState
    data object Empty : FavoritesUiState
    data class Error(val message: String) : FavoritesUiState
}
