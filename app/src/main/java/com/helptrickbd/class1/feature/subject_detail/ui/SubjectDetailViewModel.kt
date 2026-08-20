package com.helptrickbd.class1.feature.subject_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.LanguageVersion
import com.helptrickbd.class1.feature.subject_detail.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SubjectRepository
) : ViewModel() {

    private val route: Screen.SubjectDetail = savedStateHandle.toRoute()
    val bookId = route.subjectId

    private val _uiState = MutableStateFlow<SubjectDetailUiState>(SubjectDetailUiState.Loading)
    val uiState: StateFlow<SubjectDetailUiState> = _uiState.asStateFlow()

    private var currentBook: Book? = null
    private var currentVersion: LanguageVersion = AppConfig.DEFAULT_LANGUAGE_VERSION
    private var expandedChapterId: String? = null

    init {
        loadBookDetails()
    }

    fun loadBookDetails() {
        viewModelScope.launch {
            _uiState.value = SubjectDetailUiState.Loading
            repository.getBookDetail(bookId)
                .catch { e ->
                    _uiState.value = SubjectDetailUiState.Error(e.message ?: "Failed to load book details")
                }
                .collect { book ->
                    if (book != null) {
                        currentBook = book
                        // Default expand first chapter for better discoverability
                        expandedChapterId = book.chapters.firstOrNull()?.chapterId
                        emitSuccessState()
                    } else {
                        _uiState.value = SubjectDetailUiState.Error("Book not found")
                    }
                }
        }
    }

    fun onVersionSelected(version: LanguageVersion) {
        currentVersion = version
        emitSuccessState()
    }

    fun onChapterToggle(chapterId: String) {
        expandedChapterId = if (expandedChapterId == chapterId) null else chapterId
        emitSuccessState()
    }

    private fun emitSuccessState() {
        val book = currentBook ?: return
        val filteredChapters = book.chapters.filter { it.version == currentVersion }
            .ifEmpty { book.chapters } // Fallback to all if version specific doesn't exist

        _uiState.value = SubjectDetailUiState.Success(
            book = book,
            selectedVersion = currentVersion,
            chapters = filteredChapters,
            expandedChapterId = expandedChapterId
        )
    }
}
