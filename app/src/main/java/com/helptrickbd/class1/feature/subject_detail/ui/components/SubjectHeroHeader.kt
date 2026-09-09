package com.helptrickbd.class1.feature.subject_detail.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Chapter
import com.helptrickbd.class1.feature.home.ui.model.SubjectThemeResolver
import kotlin.math.roundToInt

@Composable
fun SubjectHeroHeader(
    book: Book,
    modifier: Modifier = Modifier
) {
    val theme = SubjectThemeResolver.resolve(book.title)
    val totalChaps = if (book.chapters.isNotEmpty()) book.chapters.size
                     else if (book.totalChapters > 0) book.totalChapters else 1
    val completedChaps = if (book.chapters.isNotEmpty()) book.chapters.count { it.isCompleted }
                         else (book.progressPercent * totalChaps).roundToInt()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 1. Subject Identity Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = theme.containerColor,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, theme.accentColor.copy(alpha = 0.25f))
                ) {
                    Box(modifier = Modifier.padding(12.dp)) {
                        Icon(
                            imageVector = theme.primaryIcon,
                            contentDescription = theme.categoryBadge,
                            tint = theme.accentColor,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = theme.categoryBadge,
                        style = MaterialTheme.typography.labelMedium,
                        color = theme.accentColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )

                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    book.subtitle?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Reading Analytics Visual Indicator Card (Modularized)
            SubjectReadingAnalyticsCard(
                totalChapters = totalChaps,
                completedChapters = completedChaps,
                accentColor = theme.accentColor,
                containerColor = theme.containerColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubjectHeroHeaderPreview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            SubjectHeroHeader(
                book = Book(
                    bookId = "1",
                    title = "আমার বাংলা বই",
                    subtitle = "প্রথম শ্রেণি",
                    totalChapters = 10,
                    progressPercent = 0.70f,
                    chapters = listOf(
                        Chapter("c1", "ইউনিট ১", "আমার পরিচয়", isCompleted = true),
                        Chapter("c2", "ইউনিট ২", "বর্ণ শিখি", isCompleted = false)
                    )
                )
            )
        }
    }
}
