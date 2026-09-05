package com.helptrickbd.class1.feature.learn_hub.ui.slate

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.*
import com.helptrickbd.class1.feature.learn_hub.domain.provider.SlateTracingProvider

@Immutable
data class SlateUiState(
    val strokes: List<ChalkStroke> = emptyList(),
    val currentStroke: ChalkStroke? = null,
    val activeTool: SlateTool = SlateTool.CHALK,
    val brushStyle: BrushStyle = BrushStyle.CHALK,
    val strokeWidthOption: StrokeWidthOption = StrokeWidthOption.MEDIUM,
    val activeColor: Color = Color(0xFFFFFFFF),
    val boardTheme: SlateBoardTheme = SlateBoardTheme.CLASSIC_BLACKBOARD,
    val selectedCategory: SlateTracingCategory = SlateTracingCategory.VOWELS,
    val selectedTracingItem: SlateTracingItem = SlateTracingProvider.getVowels().firstOrNull() ?: SlateTracingProvider.freehandItem,
    val canUndo: Boolean = false,
    val canRedo: Boolean = false,
    val isSoundEnabled: Boolean = true,
    val showClearDialog: Boolean = false,
    val showCelebration: Boolean = false,
    val showSaveSuccess: Boolean = false,
    val showGuideAnimation: Boolean = true,
    val currentlyPlayingAudioId: String? = null
)
