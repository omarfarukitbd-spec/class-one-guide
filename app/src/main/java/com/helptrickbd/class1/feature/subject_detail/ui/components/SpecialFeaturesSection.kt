package com.helptrickbd.class1.feature.subject_detail.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.core.designsystem.atoms.GlassCard
import com.helptrickbd.class1.core.designsystem.theme.*

@Composable
fun SpecialFeaturesSection(
    features: Map<String, Boolean>,
    modifier: Modifier = Modifier
) {
    val activeFeatures = features.filter { it.value }
    if (activeFeatures.isEmpty()) return

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Special Tools & Features",
            color = TextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (features["drawing_board"] == true) {
                FeatureButton(
                    title = "Drawing Board",
                    icon = Icons.Default.Draw,
                    modifier = Modifier.weight(1f)
                )
            }
            if (features["calculator"] == true) {
                FeatureButton(
                    title = "Calculator",
                    icon = Icons.Default.Calculate,
                    modifier = Modifier.weight(1f)
                )
            }
            if (features["quiz"] == true) {
                FeatureButton(
                    title = "Quiz",
                    icon = Icons.Default.Quiz,
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
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier,
        onClick = { /* Handle tool click */ }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = CyanGlow,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
