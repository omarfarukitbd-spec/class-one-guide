package com.helptrickbd.class1.feature.pdf_viewer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfReadingTheme
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfViewMode

@Composable
fun PdfBottomScrubber(
    currentPage: Int,
    totalPages: Int,
    readingTheme: PdfReadingTheme,
    viewMode: PdfViewMode,
    onPageSelected: (Int) -> Unit,
    onThemeSelected: (PdfReadingTheme) -> Unit,
    onViewModeToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    var sliderValue by remember(currentPage) { mutableFloatStateOf(currentPage.toFloat()) }

    Surface(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .navigationBarsPadding(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        shadowElevation = 8.dp,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "পৃষ্ঠা: $currentPage / $totalPages",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            val nextTheme = when (readingTheme) {
                                PdfReadingTheme.LIGHT -> PdfReadingTheme.DARK_INVERTED
                                PdfReadingTheme.DARK_INVERTED -> PdfReadingTheme.SEPIA_WARM
                                PdfReadingTheme.SEPIA_WARM -> PdfReadingTheme.LIGHT
                            }
                            onThemeSelected(nextTheme)
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        val icon = when (readingTheme) {
                            PdfReadingTheme.LIGHT -> Icons.Default.LightMode
                            PdfReadingTheme.DARK_INVERTED -> Icons.Default.DarkMode
                            PdfReadingTheme.SEPIA_WARM -> Icons.Default.WbSunny
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = readingTheme.displayName,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = onViewModeToggle,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoStories,
                            contentDescription = viewMode.displayName,
                            tint = if (viewMode == PdfViewMode.HORIZONTAL_PAGER) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            if (totalPages > 1) {
                Slider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it },
                    onValueChangeFinished = { onPageSelected(sliderValue.toInt().coerceIn(1, totalPages)) },
                    valueRange = 1f..totalPages.toFloat(),
                    steps = if (totalPages > 2) totalPages - 2 else 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }
    }
}
