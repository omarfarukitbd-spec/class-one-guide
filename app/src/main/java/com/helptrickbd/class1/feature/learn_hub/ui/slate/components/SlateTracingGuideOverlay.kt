package com.helptrickbd.class1.feature.learn_hub.ui.slate.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.helptrickbd.class1.R
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.*
import com.helptrickbd.class1.feature.learn_hub.domain.provider.BengaliLetterStrokeRepository
import com.helptrickbd.class1.feature.learn_hub.domain.util.SlateGlyphHelper

@Composable
fun SlateTracingGuideOverlay(
    tracingItem: SlateTracingItem,
    isUserDrawing: Boolean,
    showGuide: Boolean,
    modifier: Modifier = Modifier
) {
    if (!showGuide || tracingItem.category == SlateTracingCategory.FREEHAND) return

    val handPainter = painterResource(R.drawable.ic_tracing_hand)
    val density = LocalDensity.current
    val handSizePx = remember(density) { with(density) { 50.dp.toPx() } }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val w = constraints.maxWidth.toFloat()
        val h = constraints.maxHeight.toFloat()
        val canvasSize = Size(w, h)

        val strokes = remember(tracingItem.id) {
            BengaliLetterStrokeRepository.getStrokes(tracingItem.letter, tracingItem.category, tracingItem.id)
        }

        if (strokes.isEmpty()) return@BoxWithConstraints

        val transition = rememberInfiniteTransition(label = "tracingGuide")
        val progress by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3400, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "progress"
        )
        val pulseScale by transition.animateFloat(
            initialValue = 0.85f,
            targetValue = 1.30f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 600, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse"
        )

        val alpha by animateFloatAsState(
            targetValue = if (isUserDrawing) 0f else 0.95f,
            animationSpec = tween(250),
            label = "alpha"
        )

        if (alpha > 0.01f) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val glyphBounds = computeGlyphBounds(tracingItem, canvasSize)
                SlateTracingGuidePainter.drawGuide(
                    scope = this,
                    strokes = strokes,
                    bounds = glyphBounds,
                    handPainter = handPainter,
                    handSizePx = handSizePx,
                    progress = progress,
                    pulseScale = pulseScale,
                    alpha = alpha
                )
            }
        }
    }
}

private fun computeGlyphBounds(item: SlateTracingItem, canvasSize: Size): LetterGlyphBounds {
    if (item.category == SlateTracingCategory.SHAPES) {
        val r = minOf(canvasSize.width, canvasSize.height) * 0.32f
        val cx = canvasSize.width / 2f
        val cy = canvasSize.height / 2f
        return LetterGlyphBounds(cx - r, cy - r, r * 2f, r * 2f)
    }

    val text = if (item.letter.isNotBlank()) item.letter else item.name
    return SlateGlyphHelper.computeExactMetrics(text, canvasSize).bounds
}
