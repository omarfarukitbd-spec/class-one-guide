package com.helptrickbd.class1.feature.learn_hub.domain.model.slate

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.helptrickbd.class1.R

enum class SlateTool {
    CHALK,
    ERASER
}

enum class BrushStyle(val titleRes: Int) {
    CHALK(R.string.slate_brush_chalk),
    NEON_GLOW(R.string.slate_brush_neon),
    MARKER(R.string.slate_brush_marker),
    PENCIL(R.string.slate_brush_pencil)
}

enum class StrokeWidthOption(val strokeWidth: Float, val labelRes: Int) {
    THIN(6f, R.string.slate_size_thin),
    MEDIUM(14f, R.string.slate_size_medium),
    THICK(26f, R.string.slate_size_thick)
}

enum class SlateBoardTheme(
    val titleRes: Int,
    val boardColor: Color,
    val frameColor: Color,
    val gridColor: Color
) {
    CLASSIC_BLACKBOARD(
        titleRes = R.string.slate_theme_blackboard,
        boardColor = Color(0xFF1E222A),
        frameColor = Color(0xFF5C381E),
        gridColor = Color(0x18FFFFFF)
    ),
    CLASSROOM_GREEN(
        titleRes = R.string.slate_theme_greenboard,
        boardColor = Color(0xFF1B382B),
        frameColor = Color(0xFF6D4C41),
        gridColor = Color(0x18FFFFFF)
    ),
    MAGIC_NEON(
        titleRes = R.string.slate_theme_neon,
        boardColor = Color(0xFF0F172A),
        frameColor = Color(0xFF1E293B),
        gridColor = Color(0x2238BDF8)
    ),
    ART_PAPER(
        titleRes = R.string.slate_theme_paper,
        boardColor = Color(0xFFFFFDF8),
        frameColor = Color(0xFFD7CCC8),
        gridColor = Color(0x14000000)
    )
}

enum class SlateTracingCategory(val titleRes: Int) {
    FREEHAND(R.string.slate_cat_freehand),
    VOWELS(R.string.slate_cat_vowels),
    CONSONANTS(R.string.slate_cat_consonants),
    NUMBERS(R.string.slate_cat_numbers),
    SHAPES(R.string.slate_cat_shapes)
}

@Immutable
data class ChalkStroke(
    val id: Long,
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Float,
    val brushStyle: BrushStyle = BrushStyle.CHALK,
    val isEraser: Boolean = false
)

@Immutable
data class SlateTracingItem(
    val id: String,
    val letter: String,
    val name: String,
    val audioPath: String? = null,
    val category: SlateTracingCategory
)
