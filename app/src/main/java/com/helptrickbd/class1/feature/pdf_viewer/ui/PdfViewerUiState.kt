package com.helptrickbd.class1.feature.pdf_viewer.ui

import androidx.compose.runtime.Immutable
import com.helptrickbd.class1.feature.pdf_viewer.domain.engine.PdfRendererEngine
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.Bookmark
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfActiveSheet
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfReadingTheme
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfViewMode
import java.io.File

/**
 * Immutable 4-State UI modeling for Advanced PDF Reader.
 */
@Immutable
sealed interface PdfViewerUiState {
    @Immutable
    data class Loading(val progress: Float = 0f) : PdfViewerUiState

    @Immutable
    data class Success(
        val file: File,
        val totalPages: Int,
        val currentPage: Int = 1,
        val readingTheme: PdfReadingTheme = PdfReadingTheme.LIGHT,
        val viewMode: PdfViewMode = PdfViewMode.VERTICAL_SCROLL,
        val zoomScale: Float = 1f,
        val engine: PdfRendererEngine? = null,
        val isControlsVisible: Boolean = true,
        val activeSheet: PdfActiveSheet = PdfActiveSheet.NONE,
        val bookmarks: List<Bookmark> = emptyList(),
        val isCurrentPageBookmarked: Boolean = false
    ) : PdfViewerUiState

    @Immutable
    data class Error(val message: String) : PdfViewerUiState
}
