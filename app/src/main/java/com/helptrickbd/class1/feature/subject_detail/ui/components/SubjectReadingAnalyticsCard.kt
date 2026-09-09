package com.helptrickbd.class1.feature.subject_detail.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.core.util.toBanglaDigit
import kotlin.math.roundToInt

@Composable
fun SubjectReadingAnalyticsCard(
    totalChapters: Int,
    completedChapters: Int,
    accentColor: Color,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    val total = if (totalChapters > 0) totalChapters else 1
    val completed = completedChapters.coerceIn(0, total)
    val targetProgress = (completed.toFloat() / total.toFloat()).coerceIn(0f, 1f)

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
        label = "ReadingProgressAnimation"
    )
    val progressPercentInt = (animatedProgress * 100).roundToInt()

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ratio stat: e.g. "১০ টির মধ্যে ৩ টি অধ্যায় সম্পন্ন"
                Text(
                    text = stringResource(
                        R.string.label_reading_progress_stat,
                        total.toBanglaDigit(),
                        completed.toBanglaDigit()
                    ),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                // Percentage badge: e.g. "৩০%"
                Surface(
                    color = if (progressPercentInt == 100) Color(0xFF10B981).copy(alpha = 0.15f)
                            else containerColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${progressPercentInt.toBanglaDigit()}%",
                        fontSize = 13.sp,
                        color = if (progressPercentInt == 100) Color(0xFF047857) else accentColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Smooth Animated Progress Bar
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = if (progressPercentInt == 100) Color(0xFF10B981) else accentColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Status Chip: e.g. "পড়া চলমান" / "সম্পূর্ণ পড়া শেষ! চমৎকার!"
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (progressPercentInt == 100) Icons.Rounded.CheckCircle
                                  else Icons.Rounded.AutoStories,
                    contentDescription = null,
                    tint = if (progressPercentInt == 100) Color(0xFF10B981)
                           else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = when {
                        progressPercentInt == 100 -> stringResource(R.string.label_all_chapters_completed)
                        progressPercentInt > 0 -> stringResource(R.string.label_reading_in_progress)
                        else -> stringResource(R.string.label_start_studying)
                    },
                    fontSize = 11.sp,
                    color = if (progressPercentInt == 100) Color(0xFF047857)
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubjectReadingAnalyticsCardPreview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            SubjectReadingAnalyticsCard(
                totalChapters = 10,
                completedChapters = 7,
                accentColor = Color(0xFF007A33),
                containerColor = Color(0xFFE8F5E9)
            )
        }
    }
}
