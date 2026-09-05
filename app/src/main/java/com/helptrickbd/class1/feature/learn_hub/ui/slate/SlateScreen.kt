package com.helptrickbd.class1.feature.learn_hub.ui.slate

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Gesture
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateTool
import com.helptrickbd.class1.feature.learn_hub.ui.slate.components.*

@Composable
fun SlateScreen(
    viewModel: SlateViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        when {
            uiState.showClearDialog -> viewModel.dismissClearDialog()
            uiState.showCelebration -> viewModel.dismissCelebration()
            uiState.showSaveSuccess -> viewModel.dismissSaveSuccess()
            else -> onBackClick()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            StandardTopBar(
                title = stringResource(R.string.slate_title),
                subtitle = stringResource(R.string.slate_subtitle),
                navigationIcon = Icons.Rounded.ArrowBack,
                onNavigationClick = onBackClick,
                actions = {
                    IconButton(onClick = { viewModel.toggleGuideAnimation() }) {
                        Icon(
                            Icons.Rounded.Gesture,
                            contentDescription = stringResource(
                                if (uiState.showGuideAnimation) R.string.slate_guide_toggle_off else R.string.slate_guide_toggle_on
                            ),
                            tint = if (uiState.showGuideAnimation) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    }
                    IconButton(onClick = { viewModel.triggerCelebration() }) {
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = stringResource(R.string.slate_celebration_title),
                            tint = Color(0xFFF59E0B)
                        )
                    }
                    IconButton(onClick = { viewModel.saveDrawingToGallery() }) {
                        Icon(
                            Icons.Rounded.PhotoCamera,
                            contentDescription = stringResource(R.string.slate_save_art)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SlateTracingStrip(
                selectedCategory = uiState.selectedCategory,
                selectedItem = uiState.selectedTracingItem,
                isPlayingAudio = uiState.currentlyPlayingAudioId != null,
                onCategorySelect = viewModel::onCategorySelect,
                onItemSelect = viewModel::onTracingItemSelect,
                onPlayAudio = viewModel::playCurrentLetterAudio
            )

            Box(modifier = Modifier.weight(1f)) {
                SlateBoardFrame(theme = uiState.boardTheme) {
                    SlateCanvas(
                        strokes = uiState.strokes,
                        currentStroke = uiState.currentStroke,
                        tracingItem = uiState.selectedTracingItem,
                        boardTheme = uiState.boardTheme,
                        onStrokeStart = viewModel::onStrokeStart,
                        onStrokeDrag = viewModel::onStrokeDrag,
                        onStrokeEnd = viewModel::onStrokeEnd
                    )
                    SlateTracingGuideOverlay(
                        tracingItem = uiState.selectedTracingItem,
                        isUserDrawing = uiState.currentStroke != null,
                        showGuide = uiState.showGuideAnimation
                    )
                    SlateCelebrationOverlay(
                        visible = uiState.showCelebration,
                        onDismiss = viewModel::dismissCelebration
                    )
                }
            }

            SlateColorPickerRow(
                selectedColor = uiState.activeColor,
                onColorSelect = viewModel::onColorSelect
            )

            SlateBrushStyleRow(
                selectedBrush = uiState.brushStyle,
                selectedWidth = uiState.strokeWidthOption,
                isEraserMode = uiState.activeTool == SlateTool.ERASER,
                onBrushSelect = viewModel::onBrushStyleSelect,
                onWidthSelect = viewModel::onStrokeWidthSelect
            )

            SlateToolbar(
                activeTool = uiState.activeTool,
                currentTheme = uiState.boardTheme,
                canUndo = uiState.canUndo,
                canRedo = uiState.canRedo,
                isSoundEnabled = uiState.isSoundEnabled,
                onToolSelect = viewModel::onToolSelect,
                onThemeSelect = viewModel::onBoardThemeSelect,
                onUndo = viewModel::undo,
                onRedo = viewModel::redo,
                onClear = viewModel::promptClearSlate,
                onToggleSound = viewModel::toggleSound
            )
        }

        if (uiState.showClearDialog) {
            ClearSlateDialog(
                onConfirm = viewModel::confirmClearSlate,
                onDismiss = viewModel::dismissClearDialog
            )
        }

        if (uiState.showSaveSuccess) {
            SaveSuccessDialog(
                onDismiss = viewModel::dismissSaveSuccess
            )
        }
    }
}
