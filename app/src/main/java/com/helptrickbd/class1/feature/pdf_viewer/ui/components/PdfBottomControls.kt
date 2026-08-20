package com.helptrickbd.class1.feature.pdf_viewer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PdfBottomControls(
    currentPage: Int,
    totalPages: Int,
    isNightMode: Boolean,
    onToggleNightMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .navigationBarsPadding(), // Consumes navigation bar insets
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        shadowElevation = 8.dp,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "পৃষ্ঠা: $currentPage / $totalPages",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = onToggleNightMode,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (isNightMode) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                )
            ) {
                Icon(
                    imageVector = if (isNightMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = if (isNightMode) "লাইট মোড" else "নাইট মোড",
                    tint = if (isNightMode) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
