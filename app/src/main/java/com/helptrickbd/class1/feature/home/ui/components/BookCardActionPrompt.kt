package com.helptrickbd.class1.feature.home.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.home.ui.model.SubjectThemeConfig

@Composable
fun BookCardActionPrompt(
    progressPercent: Float,
    theme: SubjectThemeConfig,
    modifier: Modifier = Modifier
) {
    Surface(
        color = theme.containerColor,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (progressPercent > 0f) "চালিয়ে যান" else "পড়া শুরু করুন",
                fontSize = 12.sp,
                color = theme.onContainerColor,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                tint = theme.primaryColor,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
