package com.helptrickbd.class1.feature.pdf_viewer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfActiveSheet
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfViewMode
import com.helptrickbd.class1.feature.pdf_viewer.ui.components.*

@Composable
fun PdfViewerScreen(
    title: String,
    pdfUrl: String,
    viewModel: PdfViewerViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    bookId: String = "",
    initialPage: Int = 1
) {
    LaunchedEffect(pdfUrl, bookId) {
        viewModel.loadPdf(url = pdfUrl, bookId = bookId, initialPage = initialPage)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            val successState = uiState as? PdfViewerUiState.Success
            AnimatedVisibility(
                visible = successState?.isControlsVisible ?: true,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
            ) {
                PdfTopAppBar(
                    title = title,
                    currentPage = successState?.currentPage ?: initialPage,
                    totalPages = successState?.totalPages ?: 1,
                    isBookmarked = successState?.isCurrentPageBookmarked ?: false,
                    onBackClick = onBackClick,
                    onToggleBookmark = viewModel::toggleBookmark,
                    onOpenThumbnails = { viewModel.openSheet(PdfActiveSheet.THUMBNAIL_GRID) },
                    onOpenBookmarks = { viewModel.openSheet(PdfActiveSheet.BOOKMARKS_LIST) },
                    onOpenSettings = { viewModel.openSheet(PdfActiveSheet.READING_SETTINGS) }
                )
            }
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is PdfViewerUiState.Loading -> {
                PdfLoadingView(progress = state.progress, innerPadding = innerPadding)
            }
            is PdfViewerUiState.Success -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (state.viewMode == PdfViewMode.HORIZONTAL_PAGER) {
                        PdfHorizontalPagerView(
                            totalPages = state.totalPages,
                            currentPage = state.currentPage,
                            engine = state.engine,
                            readingTheme = state.readingTheme,
                            onPageChanged = viewModel::onPageChanged,
                            onToggleControls = viewModel::toggleControlsVisibility,
                            innerPadding = innerPadding
                        )
                    } else {
                        PdfVerticalListView(
                            totalPages = state.totalPages,
                            currentPage = state.currentPage,
                            engine = state.engine,
                            readingTheme = state.readingTheme,
                            onPageChanged = viewModel::onPageChanged,
                            onToggleControls = viewModel::toggleControlsVisibility,
                            innerPadding = innerPadding
                        )
                    }

                    AnimatedVisibility(
                        visible = state.isControlsVisible,
                        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        PdfBottomScrubber(
                            currentPage = state.currentPage,
                            totalPages = state.totalPages,
                            readingTheme = state.readingTheme,
                            viewMode = state.viewMode,
                            onPageSelected = viewModel::onPageChanged,
                            onThemeSelected = viewModel::setReadingTheme,
                            onViewModeToggle = {
                                val nextMode = if (state.viewMode == PdfViewMode.VERTICAL_SCROLL) PdfViewMode.HORIZONTAL_PAGER else PdfViewMode.VERTICAL_SCROLL
                                viewModel.setViewMode(nextMode)
                            }
                        )
                    }

                    when (state.activeSheet) {
                        PdfActiveSheet.THUMBNAIL_GRID -> {
                            PdfThumbnailGridSheet(
                                totalPages = state.totalPages,
                                currentPage = state.currentPage,
                                bookmarks = state.bookmarks,
                                engine = state.engine,
                                onPageSelected = viewModel::onPageChanged,
                                onDismiss = viewModel::closeSheet
                            )
                        }
                        PdfActiveSheet.BOOKMARKS_LIST -> {
                            PdfBookmarksSheet(
                                bookmarks = state.bookmarks,
                                onPageSelected = viewModel::onPageChanged,
                                onDeleteBookmark = viewModel::deleteBookmark,
                                onDismiss = viewModel::closeSheet
                            )
                        }
                        PdfActiveSheet.READING_SETTINGS -> {
                            PdfReadingSettingsSheet(
                                readingTheme = state.readingTheme,
                                viewMode = state.viewMode,
                                onThemeSelected = viewModel::setReadingTheme,
                                onViewModeSelected = viewModel::setViewMode,
                                onDismiss = viewModel::closeSheet
                            )
                        }
                        PdfActiveSheet.NONE -> {}
                    }
                }
            }
            is PdfViewerUiState.Error -> {
                PdfErrorView(message = state.message, onRetry = viewModel::retry, innerPadding = innerPadding)
            }
        }
    }
}
