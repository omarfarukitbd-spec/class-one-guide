package com.helptrickbd.class1.feature.drawing.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.ui.components.*

@Composable
fun DrawingSlateScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DrawingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(SlateCanvasBackground),
        topBar = {
            StandardTopBar(
                title = "বর্ণমালা ও সংখ্যা লেখার স্লেট",
                subtitle = "হাতে-কলমে লেখার সহজ অনুশীলন",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(SlateCanvasBackground)
        ) {
            TracingCategoryTabs(
                categories = uiState.categories,
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = viewModel::selectCategory
            )

            if (uiState.selectedCategory != TracingCategory.FREE_DRAW) {
                TracingLetterStrip(
                    items = uiState.items,
                    selectedItem = uiState.selectedItem,
                    onItemSelected = viewModel::selectItem
                )

                TracingWordBanner(
                    item = uiState.selectedItem,
                    onPrevious = viewModel::previousItem,
                    onNext = viewModel::nextItem
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                TracingCanvas(
                    paths = uiState.paths,
                    selectedItem = uiState.selectedItem,
                    selectedCategory = uiState.selectedCategory,
                    showGuide = uiState.showGuide,
                    selectedColor = uiState.selectedColor,
                    strokeWidth = uiState.strokeWidth,
                    isEraser = uiState.isEraser,
                    onPathDrawn = viewModel::addPath
                )

                DrawingControlsBar(
                    selectedColor = uiState.selectedColor,
                    isEraser = uiState.isEraser,
                    showGuide = uiState.showGuide,
                    isTracingMode = uiState.selectedCategory != TracingCategory.FREE_DRAW,
                    onColorSelected = viewModel::selectColor,
                    onToggleEraser = viewModel::toggleEraser,
                    onToggleGuide = viewModel::toggleGuide,
                    onClear = viewModel::clearCanvas,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}
