package com.helptrickbd.class1.feature.pdf_viewer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.*
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
    var isDragging by remember { mutableStateOf(false) }
    var showJumpDialog by remember { mutableStateOf(false) }

    if (showJumpDialog) {
        PageJumpDialog(
            currentPage = currentPage,
            totalPages = totalPages,
            onPageSelected = onPageSelected,
            onDismiss = { showJumpDialog = false }
        )
    }

    Surface(
        modifier = modifier
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .navigationBarsPadding(),
        shape = RoundedCornerShape(26.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
        shadowElevation = 10.dp,
        border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Live Dragging Tooltip & Top Status Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Page Indicator Badge (Tappable for Page Jump Dialog)
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.clickable { showJumpDialog = true }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.MenuBook,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isDragging) "পৃষ্ঠা: ${sliderValue.toInt()} / $totalPages" else "পৃষ্ঠা: $currentPage / $totalPages",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }

                // Quick Tools (Theme + ViewMode Switchers)
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
                        modifier = Modifier.size(34.dp)
                    ) {
                        val icon = when (readingTheme) {
                            PdfReadingTheme.LIGHT -> Icons.Rounded.LightMode
                            PdfReadingTheme.DARK_INVERTED -> Icons.Rounded.DarkMode
                            PdfReadingTheme.SEPIA_WARM -> Icons.Rounded.WbSunny
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
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = if (viewMode == PdfViewMode.HORIZONTAL_PAGER) Icons.Rounded.AutoStories else Icons.Rounded.SwapVert,
                            contentDescription = viewMode.displayName,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Interactive Scrubber with ◀ and ▶ Step Buttons
            if (totalPages > 1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (currentPage > 1) onPageSelected(currentPage - 1)
                        },
                        enabled = currentPage > 1,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                            contentDescription = "পূর্ববর্তী পৃষ্ঠা",
                            tint = if (currentPage > 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Slider(
                        value = sliderValue,
                        onValueChange = {
                            isDragging = true
                            sliderValue = it
                        },
                        onValueChangeFinished = {
                            isDragging = false
                            onPageSelected(sliderValue.toInt().coerceIn(1, totalPages))
                        },
                        valueRange = 1f..totalPages.toFloat(),
                        steps = if (totalPages > 2) totalPages - 2 else 0,
                        modifier = Modifier
                            .weight(1f)
                            .height(28.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )

                    IconButton(
                        onClick = {
                            if (currentPage < totalPages) onPageSelected(currentPage + 1)
                        },
                        enabled = currentPage < totalPages,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                            contentDescription = "পরবর্তী পৃষ্ঠা",
                            tint = if (currentPage < totalPages) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
