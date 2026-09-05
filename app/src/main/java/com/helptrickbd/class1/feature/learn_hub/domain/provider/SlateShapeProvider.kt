package com.helptrickbd.class1.feature.learn_hub.domain.provider

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateTracingCategory
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateTracingItem
import kotlin.math.cos
import kotlin.math.sin

object SlateShapeProvider {

    fun getShapeItems(): List<SlateTracingItem> = listOf(
        SlateTracingItem("shape_circle", "বৃত্ত", "বৃত্ত (Circle)", "audio/shapes/shape_circle.mp3", SlateTracingCategory.SHAPES),
        SlateTracingItem("shape_triangle", "ত্রিভুজ", "ত্রিভুজ (Triangle)", "audio/shapes/shape_triangle.mp3", SlateTracingCategory.SHAPES),
        SlateTracingItem("shape_square", "চতুর্ভুজ", "চতুর্ভুজ (Square)", "audio/shapes/shape_square.mp3", SlateTracingCategory.SHAPES),
        SlateTracingItem("shape_star", "তারা", "তারা (Star)", "audio/shapes/shape_star.mp3", SlateTracingCategory.SHAPES),
        SlateTracingItem("shape_crescent", "চাঁদ", "চাঁদ (Crescent)", "audio/shapes/shape_crescent.mp3", SlateTracingCategory.SHAPES),
        SlateTracingItem("shape_hexagon", "ষড়ভুজ", "ষড়ভুজ (Hexagon)", "audio/shapes/shape_hexagon.mp3", SlateTracingCategory.SHAPES)
    )

    fun createShapePath(id: String, size: Size): Path {
        val path = Path()
        val cx = size.width / 2f
        val cy = size.height / 2f
        val radius = minOf(size.width, size.height) * 0.32f

        when (id) {
            "shape_circle" -> {
                path.addOval(Rect(cx - radius, cy - radius, cx + radius, cy + radius))
            }
            "shape_triangle" -> {
                path.moveTo(cx, cy - radius)
                path.lineTo(cx + radius * 0.866f, cy + radius * 0.5f)
                path.lineTo(cx - radius * 0.866f, cy + radius * 0.5f)
                path.close()
            }
            "shape_square" -> {
                path.addRect(Rect(cx - radius * 0.8f, cy - radius * 0.8f, cx + radius * 0.8f, cy + radius * 0.8f))
            }
            "shape_star" -> {
                val outerRadius = radius
                val innerRadius = radius * 0.42f
                for (i in 0 until 10) {
                    val r = if (i % 2 == 0) outerRadius else innerRadius
                    val angle = (i * 36 - 90) * (Math.PI / 180.0)
                    val x = (cx + r * cos(angle)).toFloat()
                    val y = (cy + r * sin(angle)).toFloat()
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                path.close()
            }
            "shape_crescent" -> {
                path.addArc(Rect(cx - radius, cy - radius, cx + radius, cy + radius), 40f, 280f)
                path.arcTo(Rect(cx - radius * 0.4f, cy - radius, cx + radius * 1.1f, cy + radius), 270f, -180f, false)
                path.close()
            }
            "shape_hexagon" -> {
                for (i in 0 until 6) {
                    val angle = (i * 60 - 30) * (Math.PI / 180.0)
                    val x = (cx + radius * cos(angle)).toFloat()
                    val y = (cy + radius * sin(angle)).toFloat()
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                path.close()
            }
            else -> {
                path.addOval(Rect(cx - radius, cy - radius, cx + radius, cy + radius))
            }
        }
        return path
    }
}
