package com.helptrickbd.class1.feature.pdf_viewer.ui

import android.content.Context
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
import com.helptrickbd.class1.core.security.PdfCryptoEngine
import com.helptrickbd.class1.core.analytics.domain.AnalyticsTracker
import com.helptrickbd.class1.core.analytics.domain.CrashReporter
import com.helptrickbd.class1.feature.pdf_viewer.domain.usecase.SaveReadingProgressUseCase
import com.helptrickbd.class1.feature.pdf_viewer.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.util.UiText
import com.helptrickbd.class1.core.util.StorageProvider

sealed interface PdfViewerUiEvent {
    data class ShowToast(val message: UiText) : PdfViewerUiEvent
}

@HiltViewModel
class PdfViewerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val pdfRepository: PdfRepository,
    private val saveReadingProgressUseCase: SaveReadingProgressUseCase,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val cryptoEngine: PdfCryptoEngine,
    private val analyticsTracker: AnalyticsTracker,
    private val crashReporter: CrashReporter,
    private val storageProvider: StorageProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<PdfViewerUiState>(PdfViewerUiState.Loading(0f))
    val uiState: StateFlow<PdfViewerUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<PdfViewerUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var activeEngine: PdfRendererEngine? = null
    private var loadedUrl: String? = null
    var currentBookId: String? = null
        private set
    private var bookmarkJob: Job? = null

    /**
     * Loads PDF from URL or Local Cache with DRM protection.
     * Logic Fix: Only recreates engine if URL has materially changed.
     */
    fun loadPdf(url: String, bookId: String? = null, initialPage: Int = 1) {
        if (url == loadedUrl && _uiState.value is PdfViewerUiState.Success) return
        
        loadedUrl = url
        currentBookId = bookId

        viewModelScope.launch {
            analyticsTracker.logScreenView("PdfViewerScreen", bookId)
        }

        observeBookmarks(bookId)

        viewModelScope.launch {
            _uiState.value = PdfViewerUiState.Loading(0.05f)
            pdfRepository.getPdfFile(url).collect { state ->
                when (state) {
                    is DownloadState.Progress -> {
                        _uiState.value = PdfViewerUiState.Loading(state.progress)
                    }
                    is DownloadState.Success -> {
                        // Lifecycle Fix: Ensure old engine is shredded before opening new one
                        activeEngine?.close()
                        
                        val engine = PdfRendererEngine(context, storageProvider.cacheDir, state.file, cryptoEngine)
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
                            _uiState.value = PdfViewerUiState.Empty(UiText.StringResource(R.string.error_invalid_or_empty_pdf))
                        }
                    }
                    is DownloadState.Error -> {
                        val errorMsg = state.message
                        _uiState.value = PdfViewerUiState.Error(UiText.DynamicString(errorMsg))
                        crashReporter.recordException(Exception("PdfDownloadError: $errorMsg for book $bookId"))
                        _uiEvent.send(PdfViewerUiEvent.ShowToast(UiText.StringResource(R.string.error_downloading_pdf, errorMsg)))
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
            viewModelScope.launch {
                analyticsTracker.logEvent("pdf_page_turned", mapOf("page" to page, "book_id" to currentBookId.orEmpty()))
            }
        }
    }

    fun toggleBookmark() {
        val currentState = _uiState.value
        if (currentState is PdfViewerUiState.Success) {
            viewModelScope.launch {
                val isAdded = toggleBookmarkUseCase(
                    bookId = currentBookId,
                    pageNumber = currentState.currentPage,
                    title = "পৃষ্ঠা ${currentState.currentPage}"
                )
                val msgRes = if (!isAdded) R.string.msg_bookmark_removed else R.string.msg_bookmark_added
                _uiEvent.send(PdfViewerUiEvent.ShowToast(UiText.StringResource(msgRes)))
            }
        }
    }

    fun deleteBookmark(bookmarkId: Long) {
        viewModelScope.launch {
            deleteBookmarkUseCase(bookmarkId)
            _uiEvent.send(PdfViewerUiEvent.ShowToast(UiText.StringResource(R.string.msg_bookmark_removed)))
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
