package com.helptrickbd.class1.feature.home.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.core.util.toBanglaDigit
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.ui.model.SubjectThemeResolver
import kotlin.math.roundToInt

/**
 * A grid-style card for displaying a book on the Home screen.
 * Optimized for visual consistency and localized accessibility.
 */
@Composable
fun BookGridCard(
    book: Book,
    onClick: () -> Unit,
    onToggleFavorite: (() -> Unit)? = null,
    matchedLessonHighlight: String? = null,
    modifier: Modifier = Modifier
) {
    val theme = SubjectThemeResolver.resolve(book.title)
    val progressPercent = (book.progressPercent * 100).roundToInt()

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Upper Content Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header Row: Book Icon + Bookmark Icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Surface(
                        color = theme.containerColor,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.size(54.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Rounded.MenuBook,
                                contentDescription = null,
                                tint = theme.accentColor,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    if (onToggleFavorite != null) {
                        IconButton(
                            onClick = onToggleFavorite,
                            modifier = Modifier.size(32.dp).offset(x = 8.dp, y = (-8).dp)
                        ) {
                            Icon(
                                imageVector = if (book.isFavorite) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                                contentDescription = if (book.isFavorite) {
                                    stringResource(R.string.desc_remove_bookmark)
                                } else {
                                    stringResource(R.string.desc_add_bookmark)
                                },
                                tint = if (book.isFavorite) theme.accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Book Title
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    maxLines = 1
                )
                
                Spacer(modifier = Modifier.height(4.dp))

                // Subtitle / Search Highlight
                Text(
                    text = matchedLessonHighlight?.let { stringResource(R.string.label_matched, it) }
                        ?: book.subtitle ?: stringResource(R.string.label_default_subtitle),
                    color = if (matchedLessonHighlight != null) theme.accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Reading Progress Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        progress = { book.progressPercent },
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = theme.accentColor,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "${progressPercent.toBanglaDigit()}%",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )

            // Bottom Action Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.label_read_book),
                    fontSize = 14.sp,
                    color = theme.accentColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = null,
                    tint = theme.accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookGridCardPreview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp).width(200.dp)) {
            BookGridCard(
                book = Book(
                    bookId = "1",
                    title = "আমার বাংলা বই",
                    progressPercent = 0.35f,
                    isFavorite = true
                ),
                onClick = {}
            )
        }
    }
}
