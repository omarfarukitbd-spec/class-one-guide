package com.helptrickbd.class1.feature.subject_detail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.home.domain.model.Resource
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
            val title = (uiState as? SubjectDetailUiState.Success)?.book?.title ?: "বইয়ের বিবরণ"
            StandardTopBar(
                title = title,
                subtitle = "অধ্যায় ও রিসোর্স নির্বাচন করুন",
                navigationIcon = Icons.AutoMirrored.Default.ArrowBack,
                onNavigationClick = onBackClick
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is SubjectDetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailContent(
    innerPadding: PaddingValues,
    state: SubjectDetailUiState.Success,
    onVersionSelected: (com.helptrickbd.class1.feature.home.domain.model.LanguageVersion) -> Unit,
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
