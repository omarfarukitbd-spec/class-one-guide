package com.helptrickbd.class1.feature.pdf_viewer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfReadingTheme
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfViewMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfReadingSettingsSheet(
    readingTheme: PdfReadingTheme,
    viewMode: PdfViewMode,
    onThemeSelected: (PdfReadingTheme) -> Unit,
    onViewModeSelected: (PdfViewMode) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "রিডার সেটিংস ও কাস্টমাইজেশন",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "রিডিং থিম ও ফিল্টার",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThemeOptionCard(
                    title = "ডে মোড",
                    icon = Icons.Default.LightMode,
                    isSelected = readingTheme == PdfReadingTheme.LIGHT,
                    onClick = { onThemeSelected(PdfReadingTheme.LIGHT) },
                    modifier = Modifier.weight(1f)
                )
                ThemeOptionCard(
                    title = "নাইট মোড",
                    icon = Icons.Default.DarkMode,
                    isSelected = readingTheme == PdfReadingTheme.DARK_INVERTED,
                    onClick = { onThemeSelected(PdfReadingTheme.DARK_INVERTED) },
                    modifier = Modifier.weight(1f)
                )
                ThemeOptionCard(
                    title = "সেপিয়া মোড",
                    icon = Icons.Default.WbSunny,
                    isSelected = readingTheme == PdfReadingTheme.SEPIA_WARM,
                    onClick = { onThemeSelected(PdfReadingTheme.SEPIA_WARM) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "স্ক্রোলিং ও পেজ স্টাইল",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThemeOptionCard(
                    title = "ভার্টিক্যাল স্ক্রোল",
                    icon = Icons.Default.SwapVert,
                    isSelected = viewMode == PdfViewMode.VERTICAL_SCROLL,
                    onClick = { onViewModeSelected(PdfViewMode.VERTICAL_SCROLL) },
                    modifier = Modifier.weight(1f)
                )
                ThemeOptionCard(
                    title = "পাতা উল্টানো মোড",
                    icon = Icons.Default.AutoStories,
                    isSelected = viewMode == PdfViewMode.HORIZONTAL_PAGER,
                    onClick = { onViewModeSelected(PdfViewMode.HORIZONTAL_PAGER) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ThemeOptionCard(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                fontSize = 11.sp
            )
        }
    }
}
