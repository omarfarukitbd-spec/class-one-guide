package com.helptrickbd.class1.feature.learn_hub.domain.util

import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import androidx.compose.ui.geometry.Size
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.LetterGlyphBounds

data class LetterGlyphMetrics(
    val originX: Float,
    val yPos: Float,
    val paint: Paint,
    val bounds: LetterGlyphBounds,
    val nativePath: Path
)

/**
 * Single Source of Truth for calculating pixel-perfect character glyph bounds
 * ensuring 100% alignment between drawn canvas text and tracing guide overlays.
 */
object SlateGlyphHelper {

    fun computeExactMetrics(
        text: String,
        canvasSize: Size
    ): LetterGlyphMetrics {
        val w = canvasSize.width
        val h = canvasSize.height
        val fontSize = minOf(w, h) * 0.52f

        val paint = Paint().apply {
            textSize = fontSize
            typeface = Typeface.DEFAULT_BOLD
            textAlign = Paint.Align.LEFT
            isAntiAlias = true
        }

        val effectiveText = if (text.isNotBlank()) text else " "
        val advance = paint.measureText(effectiveText)
        val originX = (w - advance) / 2f
        val yPos = h / 2f - (paint.descent() + paint.ascent()) / 2f

        val path = Path()
        paint.getTextPath(effectiveText, 0, effectiveText.length, originX, yPos, path)

        val rectF = RectF()
        path.computeBounds(rectF, true)

        val rawWidth = rectF.width()
        val rawHeight = rectF.height()

        val bounds = if (rawWidth > 1f && rawHeight > 1f) {
            LetterGlyphBounds(rectF.left, rectF.top, rawWidth, rawHeight)
        } else {
            val fallbackRect = Rect()
            paint.getTextBounds(effectiveText, 0, effectiveText.length, fallbackRect)
            LetterGlyphBounds(
                originX + fallbackRect.left,
                yPos + fallbackRect.top,
                fallbackRect.width().toFloat().coerceAtLeast(fontSize * 0.35f),
                fallbackRect.height().toFloat().coerceAtLeast(fontSize * 0.35f)
            )
        }

        return LetterGlyphMetrics(
            originX = originX,
            yPos = yPos,
            paint = paint,
            bounds = bounds,
            nativePath = path
        )
    }
}
