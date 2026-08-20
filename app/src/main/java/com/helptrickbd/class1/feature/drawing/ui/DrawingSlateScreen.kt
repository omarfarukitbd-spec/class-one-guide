package com.helptrickbd.class1.feature.drawing.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
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
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SlateCanvasBackground,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরে যান"
                        )
                    }
                },
                title = {
                    Column {
                        Text(
                            text = "বর্ণমালা ও সংখ্যা লেখার স্লেট",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "হাতে-কলমে লেখার সহজ ও মিষ্টি অনুশীলন",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(SlateCanvasBackground)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
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
                        isSpeaking = uiState.isSpeaking,
                        onSpeak = viewModel::speakCurrentItem,
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
                        onDoneCelebration = viewModel::triggerCelebration,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }

            // Confetti & 3-Star Reward Overlay
            ConfettiCelebrationOverlay(
                celebrationState = uiState.celebrationState,
                onDismiss = viewModel::dismissCelebration,
                onNext = viewModel::nextItem
            )
        }
    }
}
