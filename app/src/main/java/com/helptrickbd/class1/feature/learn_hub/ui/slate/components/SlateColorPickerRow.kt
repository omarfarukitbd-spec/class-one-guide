package com.helptrickbd.class1.feature.learn_hub.ui.slate.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val SlatePastelColors = listOf(
    Color(0xFFFFFFFF), // Chalk White
    Color(0xFFFDE047), // Lemon Yellow
    Color(0xFF38BDF8), // Sky Blue
    Color(0xFFF472B6), // Bubblegum Pink
    Color(0xFF4ADE80), // Lime Green
    Color(0xFFFB923C), // Sunset Orange
    Color(0xFFA78BFA)  // Lavender Purple
)

@Composable
fun SlateColorPickerRow(
    selectedColor: Color,
    onColorSelect: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SlatePastelColors.forEach { color ->
            val isSelected = selectedColor == color
            val circleSize by animateDpAsState(targetValue = if (isSelected) 36.dp else 28.dp, label = "circleSize")
            val borderWidth by animateDpAsState(targetValue = if (isSelected) 3.dp else 1.dp, label = "borderWidth")

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onColorSelect(color) },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(circleSize)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = borderWidth,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.25f),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}
