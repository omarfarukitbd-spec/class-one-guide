package com.helptrickbd.class1.feature.learn_hub.domain.model.slate

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset

/**
 * Represents a single stroke in the handwriting order of a letter or numeral.
 * Normalized coordinates are strictly between 0.0f and 1.0f relative to the glyph's bounding box.
 */
@Immutable
data class LetterStroke(
    val strokeNumber: Int,
    val normalizedPoints: List<Offset>
)

@Immutable
data class LetterGlyphBounds(
    val left: Float,
    val top: Float,
    val width: Float,
    val height: Float
) {
    fun toCanvasOffset(norm: Offset): Offset {
        return Offset(
            x = left + norm.x * width,
            y = top + norm.y * height
        )
    }
}
