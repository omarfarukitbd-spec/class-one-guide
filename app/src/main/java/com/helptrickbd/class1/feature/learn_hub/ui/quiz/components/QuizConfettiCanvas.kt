package com.helptrickbd.class1.feature.learn_hub.ui.quiz.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

private data class ConfettiParticle(
    val startX: Float,
    val startY: Float,
    val angle: Double,
    val speed: Float,
    val color: Color,
    val radius: Float
)

@Composable
fun QuizConfettiCanvas(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isActive) return

    val progress = remember { Animatable(0f) }
    val particles = remember {
        val colors = listOf(
            Color(0xFF10B981), Color(0xFFF59E0B), Color(0xFF06B6D4),
            Color(0xFF8B5CF6), Color(0xFFEC4899), Color(0xFF3B82F6)
        )
        List(40) {
            ConfettiParticle(
                startX = 0.5f,
                startY = 0.4f,
                angle = Random.nextDouble(0.0, Math.PI * 2),
                speed = Random.nextFloat() * 450f + 200f,
                color = colors.random(),
                radius = Random.nextFloat() * 6f + 4f
            )
        }
    }

    LaunchedEffect(isActive) {
        progress.snapTo(0f)
        progress.animateTo(1f, animationSpec = tween(durationMillis = 1100))
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val p = progress.value

        particles.forEach { pt ->
            val dist = pt.speed * p
            val x = (pt.startX * w) + (dist * Math.cos(pt.angle)).toFloat()
            val y = (pt.startY * h) + (dist * Math.sin(pt.angle)).toFloat() + (p * p * 150f) // gravity
            val alpha = (1f - p).coerceIn(0f, 1f)

            drawCircle(
                color = pt.color.copy(alpha = alpha),
                radius = pt.radius * (1f - p * 0.3f),
                center = Offset(x, y)
            )
        }
    }
}
