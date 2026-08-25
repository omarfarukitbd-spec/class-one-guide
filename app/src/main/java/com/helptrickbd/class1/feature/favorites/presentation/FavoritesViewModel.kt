package com.helptrickbd.class1.feature.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    val uiState: StateFlow<FavoritesUiState> = combine(
        repository.getFavoriteBooks(),
        repository.getResumeBook()
    ) { favorites, recent ->
        if (favorites.isEmpty() && (recent == null || recent.progressPercent <= 0f)) {
            FavoritesUiState.Empty
        } else {
            FavoritesUiState.Success(
                favoriteBooks = favorites,
                recentBook = if (recent != null && recent.progressPercent > 0f) recent else null
            )
        }
    }
    .catch { emit(FavoritesUiState.Error(it.localizedMessage ?: "বুকমার্ক লোড করতে সমস্যা হয়েছে")) }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FavoritesUiState.Loading
    )

    fun onToggleFavorite(bookId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(bookId, isFavorite)
        }
    }
}
