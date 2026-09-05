package com.helptrickbd.class1.feature.learn_hub.ui.slate.components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.LetterGlyphBounds
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.LetterStroke
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

object SlateTracingGuidePainter {

    private val bnDigits = listOf("১", "২", "৩", "৪", "৫")

    fun drawGuide(
        scope: DrawScope,
        strokes: List<LetterStroke>,
        bounds: LetterGlyphBounds,
        handPainter: Painter,
        handSizePx: Float,
        progress: Float,
        pulseScale: Float,
        alpha: Float
    ) {
        with(scope) {
            val totalStrokes = strokes.size
            if (totalStrokes == 0) return

            val strokeProgress = (progress * totalStrokes).coerceIn(0f, totalStrokes - 0.001f)
            val currentStrokeIndex = strokeProgress.toInt().coerceIn(0, totalStrokes - 1)
            val subProgress = strokeProgress - currentStrokeIndex

            // 1. Draw Trajectories, Arrowheads, Start Badges & Stop Targets
            strokes.forEachIndexed { sIdx, stroke ->
                val canvasPts = stroke.normalizedPoints.map { bounds.toCanvasOffset(it) }
                if (canvasPts.isEmpty()) return@forEachIndexed

                if (canvasPts.size == 1) {
                    drawStartBadge(canvasPts.first(), sIdx, sIdx == currentStrokeIndex, pulseScale, alpha)
                    return@forEachIndexed
                }

                // Dotted trajectory
                val path = Path().apply {
                    moveTo(canvasPts[0].x, canvasPts[0].y)
                    for (i in 1 until canvasPts.size) {
                        val p0 = canvasPts[i - 1]
                        val p1 = canvasPts[i]
                        quadraticBezierTo(p0.x, p0.y, (p0.x + p1.x) / 2f, (p0.y + p1.y) / 2f)
                    }
                    lineTo(canvasPts.last().x, canvasPts.last().y)
                }
                drawPath(
                    path = path,
                    color = Color(0xFFFDE047).copy(alpha = 0.45f * alpha),
                    style = Stroke(width = 4.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f))
                )

                // Directional arrowheads
                for (i in 0 until canvasPts.size - 1) {
                    drawArrowhead(canvasPts[i], canvasPts[i + 1], alpha)
                }

                // Stop Target (কোথায় থামবে)
                drawStopTarget(canvasPts.last(), alpha)

                // Start Badge (কোথায় থেকে শুরু - ১, ২, ৩)
                drawStartBadge(canvasPts.first(), sIdx, sIdx == currentStrokeIndex, pulseScale, alpha)
            }

            // 2. Animated Hand Picture (হাতের ছবি সহ গ্লাইডিং এনিমেশন)
            val activePts = strokes[currentStrokeIndex].normalizedPoints.map { bounds.toCanvasOffset(it) }
            if (activePts.isNotEmpty()) {
                val handTip = if (activePts.size >= 2) {
                    interpolate(activePts, subProgress)
                } else {
                    activePts.first()
                }

                // Glowing contact dot at chalk tip
                drawCircle(Color(0xFF00E5FF).copy(alpha = 0.85f * alpha), radius = 6f, center = handTip)
                drawCircle(Color.White.copy(alpha = 0.95f * alpha), radius = 3.5f, center = handTip)

                // Hand illustration with index fingertip at x = (21.2 / 48) * size
                val drawX = handTip.x - (21.2f / 48f) * handSizePx
                val drawY = handTip.y
                translate(left = drawX, top = drawY) {
                    with(handPainter) {
                        draw(size = Size(handSizePx, handSizePx), alpha = alpha)
                    }
                }
            }
        }
    }

    private fun DrawScope.drawStartBadge(
        startPt: Offset,
        stepIndex: Int,
        isCurrent: Boolean,
        pulseScale: Float,
        alpha: Float
    ) {
        if (isCurrent) {
            drawCircle(
                color = Color(0xFF10B981).copy(alpha = 0.35f * alpha * pulseScale),
                radius = 20f * pulseScale,
                center = startPt
            )
        }
        drawCircle(color = Color(0xFF10B981).copy(alpha = 0.95f * alpha), radius = 12f, center = startPt)
        drawCircle(color = Color.White.copy(alpha = 0.95f * alpha), radius = 10f, center = startPt)

        val digitText = bnDigits.getOrElse(stepIndex) { "${stepIndex + 1}" }
        val badgePaint = Paint().apply {
            textSize = 20f
            color = android.graphics.Color.BLACK
            typeface = Typeface.DEFAULT_BOLD
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        val textYOffset = (badgePaint.descent() + badgePaint.ascent()) / 2f
        drawContext.canvas.nativeCanvas.drawText(digitText, startPt.x, startPt.y - textYOffset, badgePaint)
    }

    private fun DrawScope.drawStopTarget(endPt: Offset, alpha: Float) {
        drawCircle(color = Color(0xFFEF4444).copy(alpha = 0.25f * alpha), radius = 14f, center = endPt)
        drawCircle(color = Color(0xFFEF4444).copy(alpha = 0.90f * alpha), radius = 8f, center = endPt, style = Stroke(2.5f))
        drawCircle(color = Color(0xFFEF4444).copy(alpha = 0.95f * alpha), radius = 3.5f, center = endPt)
    }

    private fun DrawScope.drawArrowhead(from: Offset, to: Offset, alpha: Float) {
        val dx = to.x - from.x
        val dy = to.y - from.y
        val dist = kotlin.math.hypot(dx, dy)
        if (dist < 26f) return

        val midX = (from.x + to.x) / 2f
        val midY = (from.y + to.y) / 2f
        val angle = atan2(dy, dx)
        val arrowSize = 8f

        val p1 = Offset(midX - arrowSize * cos(angle - 0.55f), midY - arrowSize * sin(angle - 0.55f))
        val p2 = Offset(midX - arrowSize * cos(angle + 0.55f), midY - arrowSize * sin(angle + 0.55f))

        drawLine(Color(0xFFF59E0B).copy(alpha = 0.85f * alpha), p1, Offset(midX, midY), 3f, StrokeCap.Round)
        drawLine(Color(0xFFF59E0B).copy(alpha = 0.85f * alpha), p2, Offset(midX, midY), 3f, StrokeCap.Round)
    }

    private fun interpolate(points: List<Offset>, progress: Float): Offset {
        val totalSegments = points.size - 1
        val scaledProgress = (progress * totalSegments).coerceIn(0f, totalSegments.toFloat())
        val index = scaledProgress.toInt().coerceAtMost(totalSegments - 1)
        val fraction = scaledProgress - index

        val p0 = points[index]
        val p1 = points[index + 1]
        return Offset(
            x = p0.x + (p1.x - p0.x) * fraction,
            y = p0.y + (p1.y - p0.y) * fraction
        )
    }
}
