package com.helptrickbd.class1.feature.subject_detail.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.home.domain.model.LanguageVersion
import com.helptrickbd.class1.feature.home.domain.model.Resource
import com.helptrickbd.class1.feature.home.domain.model.ResourceType
import com.helptrickbd.class1.feature.subject_detail.ui.components.ChapterItemCard
import com.helptrickbd.class1.feature.subject_detail.ui.components.VersionSelector

@Composable
fun SubjectDetailScreen(
    viewModel: SubjectDetailViewModel,
    onBackClick: () -> Unit,
    onResourceClick: (Resource) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            val book = (uiState as? SubjectDetailUiState.Success)?.book
            val title = book?.title ?: "বইয়ের বিবরণ"
            val isFav = book?.isFavorite == true

            StandardTopBar(
                title = title,
                subtitle = "অধ্যায় ও রিসোর্স নির্বাচন করুন",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationClick = onBackClick,
                actions = {
                    if (book != null) {
                        IconButton(onClick = viewModel::onToggleFavorite) {
                            Icon(
                                imageVector = if (isFav) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = if (isFav) "বুকমার্ক সরান" else "বুকমার্কে যোগ করুন",
                                tint = if (isFav) MaterialTheme.colorScheme.primary else Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is SubjectDetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is SubjectDetailUiState.Success -> {
                DetailContent(
                    innerPadding = innerPadding,
                    state = state,
                    onVersionSelected = viewModel::onVersionSelected,
                    onChapterToggle = viewModel::onChapterToggle,
                    onResourceClick = onResourceClick
                )
            }
            is SubjectDetailUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
private fun DetailContent(
    innerPadding: PaddingValues,
    state: SubjectDetailUiState.Success,
    onVersionSelected: (LanguageVersion) -> Unit,
    onChapterToggle: (String) -> Unit,
    onResourceClick: (Resource) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = innerPadding.calculateTopPadding() + 16.dp,
            bottom = innerPadding.calculateBottomPadding() + 24.dp,
            start = 16.dp,
            end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if (state.book.pdfUrl.isNotBlank()) {
            item {
                FullBookCtaCard(
                    title = state.book.title,
                    onClick = {
                        onResourceClick(
                            Resource(
                                resourceId = "full_book_${state.book.bookId}",
                                title = "${state.book.title} (সম্পূর্ণ বই)",
                                pdfUrl = state.book.pdfUrl,
                                type = ResourceType.TEXTBOOK
                            )
                        )
                    }
                )
            }
        }

        if (state.book.availableVersions.size > 1) {
            item {
                VersionSelector(
                    selectedVersion = state.selectedVersion,
                    onVersionSelected = onVersionSelected
                )
            }
        }

        item {
            Text(
                text = "অধ্যায় ও পাঠ্যসূচি (${state.chapters.size} টি)",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        }

        items(state.chapters, key = { it.chapterId }) { chapter ->
            ChapterItemCard(
                chapter = chapter,
                isExpanded = state.expandedChapterId == chapter.chapterId,
                onToggleExpand = { onChapterToggle(chapter.chapterId) },
                onResourceClick = onResourceClick
            )
        }
    }
}

@Composable
private fun FullBookCtaCard(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "সম্পূর্ণ বই একনজরে পড়ুন",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "বোর্ড অনুমোদিত ডিজিটাল পাঠ্যবই",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
