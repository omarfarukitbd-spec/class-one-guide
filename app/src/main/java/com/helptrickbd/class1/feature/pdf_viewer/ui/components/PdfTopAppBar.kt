package com.helptrickbd.class1.feature.pdf_viewer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfTopAppBar(
    title: String,
    currentPage: Int,
    totalPages: Int,
    isBookmarked: Boolean,
    onBackClick: () -> Unit,
    onToggleBookmark: () -> Unit,
    onOpenThumbnails: () -> Unit,
    onOpenBookmarks: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier.statusBarsPadding(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ফিরে যান"
                )
            }
        },
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "পৃষ্ঠা: $currentPage / $totalPages",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        actions = {
            IconButton(onClick = onToggleBookmark) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = if (isBookmarked) "বুকমার্ক মুছুন" else "বুকমার্ক করুন",
                    tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onOpenThumbnails) {
                Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = "পৃষ্ঠা প্রিভিউ"
                )
            }
            IconButton(onClick = onOpenBookmarks) {
                Icon(
                    imageVector = Icons.Default.CollectionsBookmark,
                    contentDescription = "বুকমার্ক তালিকা"
                )
            }
            IconButton(onClick = onOpenSettings) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "রিডার সেটিংস"
                )
            }
        }
    )
}
