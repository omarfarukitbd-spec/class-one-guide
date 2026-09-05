package com.helptrickbd.class1.core.designsystem.modifiers

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A custom modifier that applies a glassmorphism (frosted glass) effect.
 * It uses a translucent background with a subtle border to create a premium glass look.
 * 
 * @param color The base translucent color (e.g., Color.White.copy(alpha = 0.7f)).
 * @param shape The shape of the glass panel.
 * @param borderStroke Width of the subtle glass border.
 */
fun Modifier.glassmorphism(
    color: Color = Color.White.copy(alpha = 0.7f),
    shape: Shape = RoundedCornerShape(16.dp),
    borderStroke: Dp = 1.dp
): Modifier = composed {
    this
        .clip(shape)
        // Apply Background color (Translucent)
        .background(color)
        // Apply subtle light reflection border
        .border(
            width = borderStroke,
            color = Color.White.copy(alpha = 0.2f),
            shape = shape
        )
}
