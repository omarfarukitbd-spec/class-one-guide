package com.helptrickbd.class1.feature.pdf_viewer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.helptrickbd.class1.feature.pdf_viewer.domain.engine.PdfRendererEngine
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfReadingTheme

@Composable
fun PdfHorizontalPagerView(
    totalPages: Int,
    currentPage: Int,
    engine: PdfRendererEngine?,
    readingTheme: PdfReadingTheme,
    onPageChanged: (Int) -> Unit,
    onToggleControls: () -> Unit,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    val pagerState = rememberPagerState(
        initialPage = (currentPage - 1).coerceIn(0, (totalPages - 1).coerceAtLeast(0)),
        pageCount = { totalPages }
    )

    // Sync state when currentPage changed externally (e.g. from thumbnail sheet or scrubber slider)
    LaunchedEffect(currentPage) {
        val targetIndex = (currentPage - 1).coerceIn(0, (totalPages - 1).coerceAtLeast(0))
        if (pagerState.currentPage != targetIndex) {
            pagerState.scrollToPage(targetIndex)
        }
    }

    // Notify page change when user swipes
    val currentPagerPage by remember { derivedStateOf { pagerState.currentPage } }
    LaunchedEffect(currentPagerPage) {
        onPageChanged(currentPagerPage + 1)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onToggleControls
            )
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding() + 80.dp
            ),
            pageSpacing = 12.dp
        ) { pageIndex ->
            PdfPageItem(
                pageIndex = pageIndex,
                engine = engine,
                readingTheme = readingTheme,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
