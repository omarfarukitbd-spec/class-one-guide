package com.helptrickbd.class1.feature.subject_detail.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.home.domain.model.Resource
import com.helptrickbd.class1.feature.home.domain.model.ResourceType

@Composable
fun ResourceActionButton(
    resource: Resource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (icon: ImageVector, iconColor: Color, containerColor: Color, subtitle: String) = when (resource.type) {
        ResourceType.TEXTBOOK -> ResourceStyle(
            Icons.Rounded.AutoStories,
            Color(0xFF00897B),
            Color(0xFFE0F2F1),
            "বোর্ড পাঠ্যবই"
        )
        ResourceType.GUIDEBOOK -> ResourceStyle(
            Icons.AutoMirrored.Rounded.MenuBook,
            Color(0xFF3949AB),
            Color(0xFFE8EAF6),
            "প্রশ্নোত্তর ও সমাধান"
        )
        ResourceType.MODEL_TEST -> ResourceStyle(
            Icons.Rounded.Quiz,
            Color(0xFFFB8C00),
            Color(0xFFFFF3E0),
            "অনুশীলনী ও পরীক্ষা"
        )
    }

    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = containerColor.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, iconColor.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = containerColor,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = resource.title,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        color = iconColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

private data class ResourceStyle(
    val icon: ImageVector,
    val iconColor: Color,
    val containerColor: Color,
    val subtitle: String
)
