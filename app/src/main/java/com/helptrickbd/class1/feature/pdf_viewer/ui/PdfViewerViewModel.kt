package com.helptrickbd.class1.feature.pdf_viewer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.feature.pdf_viewer.domain.engine.PdfRendererEngine
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.DownloadState
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfActiveSheet
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfReadingTheme
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfViewMode
import com.helptrickbd.class1.feature.pdf_viewer.domain.repository.PdfRepository
import com.helptrickbd.class1.feature.pdf_viewer.domain.usecase.DeleteBookmarkUseCase
import com.helptrickbd.class1.feature.pdf_viewer.domain.usecase.GetBookmarksUseCase
import com.helptrickbd.class1.feature.pdf_viewer.domain.usecase.SaveReadingProgressUseCase
import com.helptrickbd.class1.feature.pdf_viewer.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PdfViewerViewModel @Inject constructor(
    private val pdfRepository: PdfRepository,
    private val saveReadingProgressUseCase: SaveReadingProgressUseCase,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PdfViewerUiState>(PdfViewerUiState.Loading(0f))
    val uiState: StateFlow<PdfViewerUiState> = _uiState.asStateFlow()

    private var activeEngine: PdfRendererEngine? = null
    private var loadedUrl: String? = null
    var currentBookId: String? = null
        private set
    private var bookmarkJob: Job? = null

    fun loadPdf(url: String, bookId: String? = null, initialPage: Int = 1) {
        if (url == loadedUrl && _uiState.value is PdfViewerUiState.Success) return
        loadedUrl = url
        currentBookId = bookId

        observeBookmarks(bookId)

        viewModelScope.launch {
            _uiState.value = PdfViewerUiState.Loading(0.05f)
            pdfRepository.getPdfFile(url).collect { state ->
                when (state) {
                    is DownloadState.Progress -> {
                        _uiState.value = PdfViewerUiState.Loading(state.progress)
                    }
                    is DownloadState.Success -> {
                        activeEngine?.close()
                        val engine = PdfRendererEngine(state.file)
                        activeEngine = engine

                        if (engine.pageCount > 0) {
                            val startPage = initialPage.coerceIn(1, engine.pageCount)
                            val bookmarks = (_uiState.value as? PdfViewerUiState.Success)?.bookmarks ?: emptyList()
                            _uiState.value = PdfViewerUiState.Success(
                                file = state.file,
                                totalPages = engine.pageCount,
                                currentPage = startPage,
                                engine = engine,
                                bookmarks = bookmarks,
                                isCurrentPageBookmarked = bookmarks.any { it.pageNumber == startPage }
                            )
                        } else {
                            _uiState.value = PdfViewerUiState.Error("পিডিএফ ফাইলটি সঠিক নয় বা খালি")
                        }
                    }
                    is DownloadState.Error -> {
                        _uiState.value = PdfViewerUiState.Error(state.message)
                    }
                }
            }
        }
    }

    private fun observeBookmarks(bookId: String?) {
        bookmarkJob?.cancel()
        bookmarkJob = viewModelScope.launch {
            getBookmarksUseCase(bookId).collect { bookmarksList ->
                val currentState = _uiState.value
                if (currentState is PdfViewerUiState.Success) {
                    val isCurrentBookmarked = bookmarksList.any { it.pageNumber == currentState.currentPage }
                    _uiState.value = currentState.copy(
                        bookmarks = bookmarksList,
                        isCurrentPageBookmarked = isCurrentBookmarked
                    )
                }
            }
        }
    }

    fun onPageChanged(page: Int) {
        val currentState = _uiState.value
        if (currentState is PdfViewerUiState.Success && currentState.currentPage != page) {
            val isBookmarked = currentState.bookmarks.any { it.pageNumber == page }
            _uiState.value = currentState.copy(currentPage = page, isCurrentPageBookmarked = isBookmarked)
            viewModelScope.launch {
                saveReadingProgressUseCase(currentBookId, page, currentState.totalPages)
            }
        }
    }

    fun toggleBookmark() {
        val currentState = _uiState.value
        if (currentState is PdfViewerUiState.Success) {
            viewModelScope.launch {
                toggleBookmarkUseCase(
                    bookId = currentBookId,
                    pageNumber = currentState.currentPage,
                    title = "পৃষ্ঠা ${currentState.currentPage}"
                )
            }
        }
    }

    fun deleteBookmark(bookmarkId: Long) {
        viewModelScope.launch {
            deleteBookmarkUseCase(bookmarkId)
        }
    }

    fun setReadingTheme(theme: PdfReadingTheme) {
        val currentState = _uiState.value
        if (currentState is PdfViewerUiState.Success) {
            _uiState.value = currentState.copy(readingTheme = theme)
        }
    }

    fun setViewMode(mode: PdfViewMode) {
        val currentState = _uiState.value
        if (currentState is PdfViewerUiState.Success) {
            _uiState.value = currentState.copy(viewMode = mode)
        }
    }

    fun openSheet(sheet: PdfActiveSheet) {
        val currentState = _uiState.value
        if (currentState is PdfViewerUiState.Success) {
            _uiState.value = currentState.copy(activeSheet = sheet)
        }
    }

    fun closeSheet() {
        openSheet(PdfActiveSheet.NONE)
    }

    fun toggleControlsVisibility() {
        val currentState = _uiState.value
        if (currentState is PdfViewerUiState.Success) {
            _uiState.value = currentState.copy(isControlsVisible = !currentState.isControlsVisible)
        }
    }

    fun retry() {
        loadedUrl?.let { loadPdf(it, currentBookId) }
    }

    override fun onCleared() {
        super.onCleared()
        bookmarkJob?.cancel()
        activeEngine?.close()
        activeEngine = null
    }
}
