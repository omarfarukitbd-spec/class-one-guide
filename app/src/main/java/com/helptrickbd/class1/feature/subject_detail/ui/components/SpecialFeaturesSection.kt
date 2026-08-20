package com.helptrickbd.class1.feature.subject_detail.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.core.designsystem.atoms.GlassCard

@Composable
fun SpecialFeaturesSection(
    features: Map<String, Boolean>,
    modifier: Modifier = Modifier,
    onFeatureClick: (String) -> Unit = {}
) {
    val activeFeatures = features.filter { it.value }
    if (activeFeatures.isEmpty()) return

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "স্পেশাল টুলস ও ফিচারসমূহ",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (features["drawing_board"] == true) {
                FeatureButton(
                    title = "ডিজিটাল স্লেট",
                    icon = Icons.Default.Draw,
                    onClick = { onFeatureClick("drawing_board") },
                    modifier = Modifier.weight(1f)
                )
            }
            if (features["calculator"] == true) {
                FeatureButton(
                    title = "ক্যালকুলেটর",
                    icon = Icons.Default.Calculate,
                    onClick = { onFeatureClick("calculator") },
                    modifier = Modifier.weight(1f)
                )
            }
            if (features["quiz"] == true) {
                FeatureButton(
                    title = "কুইজ",
                    icon = Icons.Default.Quiz,
                    onClick = { onFeatureClick("quiz") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun FeatureButton(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
