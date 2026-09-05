package com.helptrickbd.class1.feature.learn_hub.domain.provider.stroke

import androidx.compose.ui.geometry.Offset
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.LetterStroke
import kotlin.math.cos
import kotlin.math.sin

/**
 * Authentic handwriting strokes for Bengali Numbers (১ থেকে ১০) and geometric shapes.
 */
object BengaliNumberShapeStrokes {

    fun getNumber(num: String): List<LetterStroke>? {
        return when (num) {
            "১" -> listOf(LetterStroke(1, listOf(Offset(0.38f, 0.38f), Offset(0.24f, 0.24f), Offset(0.42f, 0.12f), Offset(0.62f, 0.35f), Offset(0.52f, 0.90f))))
            "২" -> listOf(LetterStroke(1, listOf(Offset(0.25f, 0.35f), Offset(0.50f, 0.16f), Offset(0.72f, 0.35f), Offset(0.35f, 0.88f), Offset(0.80f, 0.88f))))
            "৩" -> listOf(LetterStroke(1, listOf(Offset(0.28f, 0.30f), Offset(0.52f, 0.16f), Offset(0.48f, 0.48f), Offset(0.72f, 0.62f), Offset(0.50f, 0.90f))))
            "৪" -> listOf(LetterStroke(1, listOf(Offset(0.50f, 0.18f), Offset(0.22f, 0.52f), Offset(0.50f, 0.88f), Offset(0.78f, 0.52f), Offset(0.50f, 0.18f))))
            "৫" -> listOf(LetterStroke(1, listOf(Offset(0.25f, 0.28f), Offset(0.70f, 0.20f), Offset(0.35f, 0.55f), Offset(0.75f, 0.88f))))
            "৬" -> listOf(LetterStroke(1, listOf(Offset(0.70f, 0.18f), Offset(0.26f, 0.55f), Offset(0.68f, 0.85f), Offset(0.32f, 0.55f))))
            "৭" -> listOf(LetterStroke(1, listOf(Offset(0.28f, 0.35f), Offset(0.50f, 0.18f), Offset(0.72f, 0.38f), Offset(0.52f, 0.90f))))
            "৮" -> listOf(LetterStroke(1, listOf(Offset(0.70f, 0.20f), Offset(0.30f, 0.52f), Offset(0.68f, 0.85f), Offset(0.28f, 0.85f))))
            "৯" -> listOf(LetterStroke(1, listOf(Offset(0.65f, 0.45f), Offset(0.35f, 0.22f), Offset(0.65f, 0.22f), Offset(0.65f, 0.90f))))
            "১০" -> listOf(
                LetterStroke(1, listOf(Offset(0.24f, 0.38f), Offset(0.16f, 0.24f), Offset(0.30f, 0.14f), Offset(0.40f, 0.35f), Offset(0.34f, 0.90f))),
                LetterStroke(2, listOf(Offset(0.70f, 0.20f), Offset(0.56f, 0.55f), Offset(0.70f, 0.90f), Offset(0.84f, 0.55f), Offset(0.70f, 0.20f)))
            )
            else -> null
        }
    }

    fun getShape(shapeId: String): List<LetterStroke> {
        val cx = 0.5f
        val cy = 0.5f
        val r = 0.42f
        val pts = when (shapeId) {
            "shape_circle" -> (0..36).map { i ->
                val angle = (i * 10 - 90) * (Math.PI / 180.0)
                Offset((cx + r * cos(angle)).toFloat(), (cy + r * sin(angle)).toFloat())
            }
            "shape_triangle" -> listOf(
                Offset(cx, cy - r), Offset(cx + r * 0.866f, cy + r * 0.5f),
                Offset(cx - r * 0.866f, cy + r * 0.5f), Offset(cx, cy - r)
            )
            "shape_square" -> listOf(
                Offset(cx - r * 0.8f, cy - r * 0.8f), Offset(cx + r * 0.8f, cy - r * 0.8f),
                Offset(cx + r * 0.8f, cy + r * 0.8f), Offset(cx - r * 0.8f, cy + r * 0.8f),
                Offset(cx - r * 0.8f, cy - r * 0.8f)
            )
            "shape_star" -> (0..10).map { i ->
                val curR = if (i % 2 == 0) r else r * 0.42f
                val angle = (i * 36 - 90) * (Math.PI / 180.0)
                Offset((cx + curR * cos(angle)).toFloat(), (cy + curR * sin(angle)).toFloat())
            }
            "shape_crescent" -> (0..24).map { i ->
                val angle = (i * 15 - 90) * (Math.PI / 180.0)
                Offset((cx + r * cos(angle)).toFloat(), (cy + r * sin(angle)).toFloat())
            }
            "shape_hexagon" -> (0..6).map { i ->
                val angle = (i * 60 - 30) * (Math.PI / 180.0)
                Offset((cx + r * cos(angle)).toFloat(), (cy + r * sin(angle)).toFloat())
            }
            else -> (0..36).map { i ->
                val angle = (i * 10 - 90) * (Math.PI / 180.0)
                Offset((cx + r * cos(angle)).toFloat(), (cy + r * sin(angle)).toFloat())
            }
        }
        return listOf(LetterStroke(1, pts))
    }
}
