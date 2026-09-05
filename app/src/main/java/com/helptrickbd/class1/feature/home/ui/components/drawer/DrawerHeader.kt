package com.helptrickbd.class1.feature.home.ui.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Brush
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.designsystem.theme.BackgroundDark

@Composable
fun DrawerHeader(modifier: Modifier = Modifier) {
    val isDark = MaterialTheme.colorScheme.background == BackgroundDark
    val headerBrush = if (isDark) {
        Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primaryContainer, // #0C2B45 Deep Midnight Navy
                MaterialTheme.colorScheme.surface           // #131B2C Smoky Glass
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary, // #004700
                Color(0xFF005A00)
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(headerBrush)
            .statusBarsPadding() // Consumes status bar insets for native edge-to-edge
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Box(modifier = Modifier.padding(10.dp)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = AppConfig.APP_NAME,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "জাতীয় শিক্ষাক্রম ও পাঠ্যপুস্তক বোর্ড",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "প্রথম শ্রেণি • নতুন শিক্ষাক্রম",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}
