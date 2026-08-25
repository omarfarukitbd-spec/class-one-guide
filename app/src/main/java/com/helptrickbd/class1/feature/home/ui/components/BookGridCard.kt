package com.helptrickbd.class1.feature.home.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
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
fun BookGridCard(
    book: Book,
    onClick: () -> Unit,
    onToggleFavorite: (() -> Unit)? = null,
    matchedLessonHighlight: String? = null,
    modifier: Modifier = Modifier
) {
    val theme = SubjectThemeResolver.resolve(book.title)
    val chapterCount = if (book.totalChapters > 0) book.totalChapters else book.chapters.size
    val progressPercent = (book.progressPercent * 100).toInt()

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, pressedElevation = 5.dp),
        border = BorderStroke(1.2.dp, theme.primaryColor.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Row: Subject Icon + Chapter Badge + Bookmark Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = theme.containerColor,
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Box(modifier = Modifier.padding(10.dp)) {
                        Icon(
                            imageVector = theme.primaryIcon,
                            contentDescription = theme.categoryBadge,
                            tint = theme.primaryColor,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = theme.primaryColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "$chapterCount টি অধ্যায়",
                            color = theme.primaryColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                        )
                    }

                    if (onToggleFavorite != null) {
                        Spacer(modifier = Modifier.width(2.dp))
                        IconButton(
                            onClick = onToggleFavorite,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = if (book.isFavorite) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                                contentDescription = if (book.isFavorite) "বুকমার্ক সরান" else "বুকমার্কে যোগ করুন",
                                tint = if (book.isFavorite) theme.primaryColor else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Subject Category Tag
            Text(
                text = theme.categoryBadge,
                style = MaterialTheme.typography.labelSmall,
                color = theme.primaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )

            // Book Title
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                maxLines = 1
            )

            // Subtitle or Search Match Result
            if (matchedLessonHighlight != null) {
                Surface(
                    color = theme.primaryColor.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                ) {
                    Text(
                        text = "মিলেছে: $matchedLessonHighlight",
                        color = theme.primaryColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            } else {
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

            Spacer(modifier = Modifier.height(12.dp))

            // Reading Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = { book.progressPercent },
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(CircleShape),
                    color = theme.primaryColor,
                    trackColor = theme.containerColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$progressPercent%",
                    style = MaterialTheme.typography.labelSmall,
                    color = theme.primaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Pill Button (Child-Friendly & 100% Readable)
            BookCardActionPrompt(
                progressPercent = book.progressPercent,
                theme = theme
            )
        }
    }
}
