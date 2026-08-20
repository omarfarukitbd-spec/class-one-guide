package com.helptrickbd.class1.feature.drawing.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val SlateColors = listOf(
    Color(0xFFFFEB3B), // Bright Yellow
    Color.White,
    Color(0xFF00E676), // Bright Green
    Color(0xFF00E5FF), // Bright Cyan
    Color(0xFFFF4081), // Vibrant Pink
    Color(0xFFFF9100), // Orange
    Color(0xFFEA80FC)  // Soft Purple
)

@Composable
fun DrawingControlsBar(
    selectedColor: Color,
    isEraser: Boolean,
    showGuide: Boolean,
    isTracingMode: Boolean,
    onColorSelected: (Color) -> Unit,
    onToggleEraser: () -> Unit,
    onToggleGuide: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        tonalElevation = 6.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SlateColors.forEach { color ->
                    val isSelected = !isEraser && selectedColor == color
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.4f),
                                shape = CircleShape
                            )
                            .clickable { onColorSelected(color) }
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isTracingMode) {
                    IconButton(
                        onClick = onToggleGuide,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (showGuide) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                        )
                    ) {
                        Icon(
                            imageVector = if (showGuide) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (showGuide) "গাইড লুকান" else "গাইড দেখান",
                            tint = if (showGuide) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                FilledTonalIconButton(
                    onClick = onToggleEraser,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = if (isEraser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isEraser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Icon(
                        imageVector = if (isEraser) Icons.Default.Edit else Icons.Default.AutoFixHigh,
                        contentDescription = "ইরেজার"
                    )
                }

                IconButton(
                    onClick = onClear,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "মুছে ফেলুন"
                    )
                }
            }
        }
    }
}
