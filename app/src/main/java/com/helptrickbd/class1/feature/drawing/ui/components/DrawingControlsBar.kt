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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val SlateColors = listOf(
    Color(0xFFFFD54F), // Radiant Yellow
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
    onDoneCelebration: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFF1E2538),
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF333E54))
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
                                color = if (isSelected) Color(0xFFFFD54F) else Color.White.copy(alpha = 0.3f),
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
                    // Done / Celebration Trigger Button
                    FilledTonalButton(
                        onClick = onDoneCelebration,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = Color(0xFFFFD54F),
                            contentColor = Color(0xFF121722)
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "সম্পন্ন",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(
                        onClick = onToggleGuide,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (showGuide) Color(0xFF283349) else Color.Transparent
                        )
                    ) {
                        Icon(
                            imageVector = if (showGuide) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (showGuide) "গাইড লুকান" else "গাইড দেখান",
                            tint = if (showGuide) Color(0xFFFFD54F) else Color(0xFF94A3B8)
                        )
                    }
                }

                FilledTonalIconButton(
                    onClick = onToggleEraser,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = if (isEraser) Color(0xFFFFD54F) else Color(0xFF283349),
                        contentColor = if (isEraser) Color(0xFF121722) else Color(0xFFF1F5F9)
                    )
                ) {
                    Icon(
                        imageVector = if (isEraser) Icons.Default.Edit else Icons.Default.AutoFixHigh,
                        contentDescription = "ইরেজার"
                    )
                }

                IconButton(
                    onClick = onClear,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0xFFFF6E6E))
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
