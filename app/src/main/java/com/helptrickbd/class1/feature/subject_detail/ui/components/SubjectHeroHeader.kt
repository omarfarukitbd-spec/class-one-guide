package com.helptrickbd.class1.feature.subject_detail.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.ui.model.SubjectThemeResolver

@Composable
fun SubjectHeroHeader(
    book: Book,
    modifier: Modifier = Modifier
) {
    val theme = SubjectThemeResolver.resolve(book.title)
    val chapterCount = if (book.totalChapters > 0) book.totalChapters else book.chapters.size
    val progressPercent = (book.progressPercent * 100).toInt()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = theme.containerColor.copy(alpha = 0.45f)
        ),
        border = BorderStroke(1.2.dp, theme.primaryColor.copy(alpha = 0.25f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = theme.containerColor,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, theme.primaryColor.copy(alpha = 0.2f))
                ) {
                    Box(modifier = Modifier.padding(12.dp)) {
                        Icon(
                            imageVector = theme.primaryIcon,
                            contentDescription = theme.categoryBadge,
                            tint = theme.primaryColor,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = theme.categoryBadge,
                        style = MaterialTheme.typography.labelMedium,
                        color = theme.primaryColor,
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

            Spacer(modifier = Modifier.height(14.dp))

            // Reading Progress Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "মোট $chapterCount টি অধ্যায়",
                    fontSize = 11.sp,
                    color = theme.primaryColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$progressPercent% সম্পন্ন",
                    fontSize = 11.sp,
                    color = theme.primaryColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { book.progressPercent },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = theme.primaryColor,
                trackColor = theme.containerColor
            )
        }
    }
}
