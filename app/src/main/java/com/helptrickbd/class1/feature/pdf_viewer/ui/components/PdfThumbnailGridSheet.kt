package com.helptrickbd.class1.feature.pdf_viewer.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.pdf_viewer.domain.engine.PdfRendererEngine
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.Bookmark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfThumbnailGridSheet(
    totalPages: Int,
    currentPage: Int,
    bookmarks: List<Bookmark>,
    engine: PdfRendererEngine?,
    onPageSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val gridState = rememberLazyGridState(
        initialFirstVisibleItemIndex = (currentPage - 1).coerceAtLeast(0)
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "পৃষ্ঠা প্রিভিউ ও ইনডেক্স ($totalPages টি পৃষ্ঠা)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = gridState,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 420.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(count = totalPages, key = { it }) { index ->
                    val pageNumber = index + 1
                    val isSelected = pageNumber == currentPage
                    val isBookmarked = bookmarks.any { it.pageNumber == pageNumber }

                    ThumbnailCard(
                        pageNumber = pageNumber,
                        isSelected = isSelected,
                        isBookmarked = isBookmarked,
                        engine = engine,
                        onClick = {
                            onPageSelected(pageNumber)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ThumbnailCard(
    pageNumber: Int,
    isSelected: Boolean,
    isBookmarked: Boolean,
    engine: PdfRendererEngine?,
    onClick: () -> Unit
) {
    val pageIndex = pageNumber - 1
    val thumbnailBitmap by produceState<Bitmap?>(initialValue = null, key1 = pageIndex) {
        value = engine?.renderThumbnail(pageIndex, targetWidth = 180)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            contentAlignment = Alignment.Center
        ) {
            val bitmap = thumbnailBitmap
            if (bitmap != null && !bitmap.isRecycled) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "পৃষ্ঠা $pageNumber",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Top badges (Bookmark & Current Page)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (isBookmarked) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "বুকমার্ক",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp).padding(2.dp)
                        )
                    }
                } else Spacer(modifier = Modifier.size(14.dp))

                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "বর্তমান পৃষ্ঠা",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Bottom Page Badge
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                shape = RoundedCornerShape(topStart = 6.dp),
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Text(
                    text = "$pageNumber",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}
