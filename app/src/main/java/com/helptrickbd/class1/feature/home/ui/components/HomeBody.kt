package com.helptrickbd.class1.feature.home.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.designsystem.components.AppSearchBar
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.presentation.HomeUiState

@Composable
fun HomeBody(
    innerPadding: PaddingValues,
    state: HomeUiState.Success,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onCurriculumSelected: (Curriculum) -> Unit,
    onBookClick: (String, String) -> Unit,
    onResumeClick: (Book) -> Unit,
    onToggleFavorite: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSearching = state.searchQuery.isNotBlank()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = innerPadding.calculateTopPadding() + 14.dp,
            bottom = innerPadding.calculateBottomPadding() + 20.dp,
            start = 16.dp,
            end = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier.fillMaxSize()
    ) {
        if (AppConfig.FEATURE_SEARCH) {
            item(span = { GridItemSpan(2) }) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    AppSearchBar(
                        query = state.searchQuery,
                        onQueryChange = onSearchQueryChange,
                        onClearQuery = onClearSearch
                    )
                    SearchSuggestionChips(onSuggestionClick = onSearchQueryChange)
                }
            }
        }

        if (isSearching) {
            if (state.searchResults.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = "অনুসন্ধানের ফলাফল (${state.searchResults.size} টি বিষয়)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(state.searchResults, key = { it.book.bookId }) { result ->
                    val highlight = if (result.matchedUnitNo != null && result.matchedChapterTitle != null) {
                        "${result.matchedUnitNo} • ${result.matchedChapterTitle}"
                    } else result.matchedChapterTitle ?: result.matchedUnitNo

                    BookGridCard(
                        book = result.book,
                        onClick = { onBookClick(result.book.bookId, result.book.title) },
                        onToggleFavorite = { onToggleFavorite(result.book.bookId, !result.book.isFavorite) },
                        matchedLessonHighlight = highlight
                    )
                }
            } else {
                item(span = { GridItemSpan(2) }) {
                    EmptySearchState(
                        query = state.searchQuery,
                        onClearSearch = onClearSearch
                    )
                }
            }
        } else {
            item(span = { GridItemSpan(2) }) {
                CloudNoticeBanner(notice = "২০২৬ শিক্ষাক্রমের সকল পাঠ্যবই ও সমাধান নিয়মিত হালনাগাদ করা হচ্ছে।")
            }

            item(span = { GridItemSpan(2) }) {
                CurriculumSelector(
                    selectedCurriculum = state.selectedCurriculum,
                    onCurriculumSelected = onCurriculumSelected
                )
            }

            state.resumeBook?.let { resume ->
                item(span = { GridItemSpan(2) }) {
                    ResumeReadingSection(
                        book = resume,
                        onClick = { onResumeClick(resume) }
                    )
                }
            }

            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "${state.selectedCurriculum.titleBangla} পাঠ্যবইসমূহ",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            }

            items(state.books, key = { it.bookId }) { book ->
                BookGridCard(
                    book = book,
                    onClick = { onBookClick(book.bookId, book.title) },
                    onToggleFavorite = { onToggleFavorite(book.bookId, !book.isFavorite) }
                )
            }
        }
    }
}
