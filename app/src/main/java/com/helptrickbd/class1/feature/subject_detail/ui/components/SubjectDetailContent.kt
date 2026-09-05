package com.helptrickbd.class1.feature.subject_detail.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.LanguageVersion
import com.helptrickbd.class1.feature.home.domain.model.Resource
import com.helptrickbd.class1.feature.home.domain.model.ResourceType
import com.helptrickbd.class1.feature.subject_detail.ui.SubjectDetailUiState

@Composable
fun SubjectDetailContent(
    innerPadding: PaddingValues,
    state: SubjectDetailUiState.Success,
    onVersionSelected: (LanguageVersion) -> Unit,
    onChapterToggle: (String) -> Unit,
    onResourceClick: (Resource) -> Unit,
    modifier: Modifier = Modifier
) {
    val fullBookSuffix = stringResource(R.string.msg_full_book_suffix)
    val chaptersTitle = stringResource(R.string.label_chapters_and_syllabus, state.chapters.size)

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = innerPadding.calculateTopPadding() + 16.dp,
            bottom = innerPadding.calculateBottomPadding() + 24.dp,
            start = 16.dp,
            end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Subject Hero Header
        item {
            SubjectHeroHeader(book = state.book)
        }

        // 2. Full Book Quick Read CTA Card
        if (state.book.pdfUrl.isNotBlank()) {
            item {
                FullBookCtaCard(
                    title = state.book.title,
                    onClick = {
                        onResourceClick(
                            Resource(
                                resourceId = "full_book_${state.book.bookId}",
                                title = "${state.book.title} $fullBookSuffix",
                                pdfUrl = state.book.pdfUrl,
                                type = ResourceType.TEXTBOOK
                            )
                        )
                    }
                )
            }
        }

        // 3. Language Version Selector
        if (state.book.availableVersions.size > 1) {
            item {
                VersionSelector(
                    selectedVersion = state.selectedVersion,
                    onVersionSelected = onVersionSelected
                )
            }
        }

        // 4. Chapter Section Title
        item {
            Text(
                text = chaptersTitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        }

        // 5. Chapter Cards
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

@Preview(showBackground = true)
@Composable
private fun SubjectDetailContentPreview() {
    AppTheme {
        SubjectDetailContent(
            innerPadding = PaddingValues(0.dp),
            state = SubjectDetailUiState.Success(
                book = Book("1", "আমার বাংলা বই", pdfUrl = "mock.pdf"),
                selectedVersion = LanguageVersion.BANGLA,
                chapters = listOf(),
                expandedChapterId = null
            ),
            onVersionSelected = {},
            onChapterToggle = {},
            onResourceClick = {}
        )
    }
}
