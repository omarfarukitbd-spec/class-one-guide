package com.helptrickbd.class1.feature.subject_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.analytics.domain.AnalyticsTracker
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.core.util.UiText
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.LanguageVersion
import com.helptrickbd.class1.feature.subject_detail.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SubjectDetailUiEvent {
    data class ShowToast(val message: UiText) : SubjectDetailUiEvent
}

@HiltViewModel
class SubjectDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SubjectRepository,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {

    private val route: Screen.SubjectDetail = savedStateHandle.toRoute()
    val bookId = route.subjectId

    private val _currentBook = MutableStateFlow<Book?>(null)
    private val _isLoading = MutableStateFlow(true)
    private val _error = MutableStateFlow<UiText?>(null)
    
    private val _currentVersion = MutableStateFlow(AppConfig.DEFAULT_LANGUAGE_VERSION)
    private val _expandedChapterId = MutableStateFlow<String?>(null)

    private val _uiEvent = Channel<SubjectDetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    // 100% Flicker-free UI State construction
    val uiState: StateFlow<SubjectDetailUiState> = combine(
        _currentBook,
        _isLoading,
        _error,
        _currentVersion,
        _expandedChapterId
    ) { book, loading, error, version, expandedId ->
        when {
            loading -> SubjectDetailUiState.Loading
            error != null -> SubjectDetailUiState.Error(error)
            book == null -> SubjectDetailUiState.Empty(UiText.StringResource(R.string.error_book_details_not_found))
            else -> {
                // Strict Version Filtering: Show only selected version, no confusing fallback
                val filteredChapters = book.chapters.filter { it.version == version }
                if (filteredChapters.isEmpty()) {
                    SubjectDetailUiState.Empty(UiText.StringResource(R.string.error_no_chapters_for_version))
                } else {
                    SubjectDetailUiState.Success(
                        book = book,
                        selectedVersion = version,
                        chapters = filteredChapters,
                        expandedChapterId = expandedId
                    )
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SubjectDetailUiState.Loading
    )

    init {
        loadBookDetails()
        logScreenView()
    }

    fun loadBookDetails() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            repository.getBookDetail(bookId)
                .catch { e ->
                    _isLoading.value = false
                    _error.value = UiText.StringResource(R.string.error_loading_book_details, e.message ?: "")
                }
                .collect { book ->
                    _isLoading.value = false
                    _currentBook.value = book
                    // Auto-expand first chapter on first load
                    if (book != null && _expandedChapterId.value == null) {
                        _expandedChapterId.value = book.chapters.firstOrNull { 
                            it.version == _currentVersion.value 
                        }?.chapterId
                    }
                }
        }
    }

    fun onToggleFavorite() {
        val book = _currentBook.value ?: return
        val newFav = !book.isFavorite
        viewModelScope.launch {
            repository.toggleFavorite(book.bookId, newFav)
            val msgRes = if (newFav) R.string.msg_added_to_favorites else R.string.msg_removed_from_favorites
            _uiEvent.send(SubjectDetailUiEvent.ShowToast(UiText.StringResource(msgRes)))
        }
    }

    fun onVersionSelected(version: LanguageVersion) {
        _currentVersion.value = version
        // Reset expansion on version change for better UX
        _expandedChapterId.value = _currentBook.value?.chapters?.firstOrNull { it.version == version }?.chapterId
    }

    fun onChapterToggle(chapterId: String) {
        _expandedChapterId.value = if (_expandedChapterId.value == chapterId) null else chapterId
        if (_expandedChapterId.value != null) {
            viewModelScope.launch {
                analyticsTracker.logEvent("chapter_opened", mapOf("chapter_id" to chapterId, "book_id" to bookId))
            }
        }
    }

    private fun logScreenView() {
        viewModelScope.launch {
            analyticsTracker.logScreenView("SubjectDetailScreen", bookId)
        }
    }
}
