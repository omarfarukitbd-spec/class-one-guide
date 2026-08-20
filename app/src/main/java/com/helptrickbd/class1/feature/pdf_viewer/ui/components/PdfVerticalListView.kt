package com.helptrickbd.class1.feature.pdf_viewer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.helptrickbd.class1.feature.pdf_viewer.domain.engine.PdfRendererEngine
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfReadingTheme

@Composable
fun PdfVerticalListView(
    totalPages: Int,
    currentPage: Int,
    engine: PdfRendererEngine?,
    readingTheme: PdfReadingTheme,
    onPageChanged: (Int) -> Unit,
    onToggleControls: () -> Unit,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (currentPage - 1).coerceAtLeast(0)
    )

    // Sync state when currentPage changed externally (e.g. from thumbnail sheet or slider)
    LaunchedEffect(currentPage) {
        val targetIndex = (currentPage - 1).coerceIn(0, (totalPages - 1).coerceAtLeast(0))
        if (!listState.isScrollInProgress && listState.firstVisibleItemIndex != targetIndex) {
            listState.scrollToItem(targetIndex)
        }
    }

    val firstVisibleItem by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    LaunchedEffect(firstVisibleItem) {
        onPageChanged(firstVisibleItem + 1)
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
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding() + 80.dp
            )
        ) {
            items(count = totalPages, key = { it }) { pageIndex ->
                PdfPageItem(
                    pageIndex = pageIndex,
                    engine = engine,
                    readingTheme = readingTheme
                )
            }
        }
    }
}
