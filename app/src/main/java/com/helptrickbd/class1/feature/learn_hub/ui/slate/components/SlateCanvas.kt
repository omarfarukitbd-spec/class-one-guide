package com.helptrickbd.class1.feature.learn_hub.ui.slate.components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.*
import com.helptrickbd.class1.feature.learn_hub.domain.provider.SlateShapeProvider
import com.helptrickbd.class1.feature.learn_hub.domain.util.SlateGlyphHelper

@Composable
fun SlateCanvas(
    strokes: List<ChalkStroke>,
    currentStroke: ChalkStroke?,
    tracingItem: SlateTracingItem,
    boardTheme: SlateBoardTheme,
    onStrokeStart: (Offset) -> Unit,
    onStrokeDrag: (Offset) -> Unit,
    onStrokeEnd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown().also { it.consume() }
                    onStrokeStart(down.position)
                    val pointerId = down.id
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull { it.id == pointerId }
                        if (change == null || !change.pressed) {
                            change?.consume()
                            onStrokeEnd()
                            break
                        }
                        change.consume()
                        onStrokeDrag(change.position)
                    }
                }
            }
    ) {
        drawRulingGuidelines(boardTheme)

        drawTracingGuide(tracingItem, boardTheme)

        strokes.forEach { stroke -> drawSmoothStroke(stroke) }
        currentStroke?.let { drawSmoothStroke(it) }
    }
}

private fun DrawScope.drawRulingGuidelines(theme: SlateBoardTheme) {
    val h = size.height
    val w = size.width
    val topY = h * 0.22f
    val matraY = h * 0.38f
    val baseY = h * 0.76f

    val dashEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), 0f)
    drawLine(theme.gridColor, Offset(16f, topY), Offset(w - 16f, topY), strokeWidth = 1.5f, pathEffect = dashEffect)
    drawLine(theme.gridColor.copy(alpha = 0.35f), Offset(16f, matraY), Offset(w - 16f, matraY), strokeWidth = 2.0f)
    drawLine(theme.gridColor, Offset(16f, baseY), Offset(w - 16f, baseY), strokeWidth = 1.5f, pathEffect = dashEffect)
}

private fun DrawScope.drawTracingGuide(item: SlateTracingItem, theme: SlateBoardTheme) {
    if (item.category == SlateTracingCategory.FREEHAND) return

    if (item.category == SlateTracingCategory.SHAPES) {
        val shapePath = SlateShapeProvider.createShapePath(item.id, size)
        drawPath(
            path = shapePath,
            color = if (theme == SlateBoardTheme.ART_PAPER) Color(0x33000000) else Color(0x38FFFFFF),
            style = Stroke(
                width = 4f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(18f, 14f), 0f)
            )
        )
    } else if (item.letter.isNotBlank()) {
        val metrics = SlateGlyphHelper.computeExactMetrics(item.letter, size)
        val faintColor = if (theme == SlateBoardTheme.ART_PAPER) {
            android.graphics.Color.argb(45, 0, 0, 0)
        } else {
            android.graphics.Color.argb(55, 255, 255, 255)
        }
        metrics.paint.color = faintColor

        drawContext.canvas.nativeCanvas.drawText(
            item.letter,
            metrics.originX,
            metrics.yPos,
            metrics.paint
        )

        val outlineColor = if (theme == SlateBoardTheme.ART_PAPER) {
            Color(0x38000000)
        } else {
            Color(0x40FFFFFF)
        }
        drawPath(
            path = metrics.nativePath.asComposePath(),
            color = outlineColor,
            style = Stroke(
                width = 2.5f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        )
    }
}

private fun DrawScope.drawSmoothStroke(stroke: ChalkStroke) {
    val pts = stroke.points
    if (pts.isEmpty()) return

    if (pts.size == 1) {
        drawCircle(stroke.color, radius = stroke.strokeWidth / 2f, center = pts[0])
        return
    }

    val path = Path().apply {
        moveTo(pts[0].x, pts[0].y)
        for (i in 1 until pts.size) {
            val p0 = pts[i - 1]
            val p1 = pts[i]
            quadraticBezierTo(p0.x, p0.y, (p0.x + p1.x) / 2f, (p0.y + p1.y) / 2f)
        }
        lineTo(pts.last().x, pts.last().y)
    }

    when (stroke.brushStyle) {
        BrushStyle.NEON_GLOW -> {
            drawPath(
                path = path,
                color = stroke.color.copy(alpha = 0.35f),
                style = Stroke(stroke.strokeWidth * 2.5f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
            drawPath(
                path = path,
                color = Color.White,
                style = Stroke(stroke.strokeWidth * 0.7f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
        BrushStyle.MARKER -> {
            drawPath(
                path = path,
                color = stroke.color.copy(alpha = 0.55f),
                style = Stroke(stroke.strokeWidth * 1.3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
        else -> {
            drawPath(
                path = path,
                color = stroke.color,
                style = Stroke(stroke.strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}
