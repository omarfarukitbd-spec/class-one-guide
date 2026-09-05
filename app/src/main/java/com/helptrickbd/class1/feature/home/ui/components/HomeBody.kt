package com.helptrickbd.class1.feature.home.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.designsystem.components.AppSearchBar
import com.helptrickbd.class1.core.designsystem.components.LayoutSwitchToggle
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.LayoutMode
import com.helptrickbd.class1.feature.home.presentation.HomeUiState

@Composable
fun HomeBody(
    innerPadding: PaddingValues,
    state: HomeUiState.Success,
    layoutMode: LayoutMode,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onCurriculumSelected: (Curriculum) -> Unit,
    onBookClick: (String, String) -> Unit,
    onResumeClick: (Book) -> Unit,
    onToggleFavorite: (String, Boolean) -> Unit,
    onToggleLayoutMode: (LayoutMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSearching = state.searchQuery.isNotBlank()
    val isGrid = layoutMode == LayoutMode.GRID
    val spanCount = if (isGrid) 2 else 1

    LazyVerticalGrid(
        columns = GridCells.Fixed(spanCount),
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
            item(span = { GridItemSpan(spanCount) }) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    AppSearchBar(
                        query = state.searchQuery,
                        onQueryChange = onSearchQueryChange,
                        onClearQuery = onClearSearch
                    )
                    SearchSuggestionChips(
                        selectedQuery = state.searchQuery,
                        onSuggestionClick = onSearchQueryChange
                    )
                }
            }
        }

        if (isSearching) {
            if (state.searchResults.isNotEmpty()) {
                item(span = { GridItemSpan(spanCount) }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "অনুসন্ধানের ফলাফল (${state.searchResults.size} টি বিষয়)",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )

                        if (AppConfig.FEATURE_LAYOUT_SWITCHER) {
                            LayoutSwitchToggle(
                                layoutMode = layoutMode,
                                onToggle = onToggleLayoutMode
                            )
                        }
                    }
                }
                items(
                    items = state.searchResults,
                    key = { "search_${it.book.bookId}_${it.matchedChapterId ?: "root"}" }
                ) { result ->
                    val highlight = if (result.matchedUnitNo != null && result.matchedChapterTitle != null) {
                        "${result.matchedUnitNo} • ${result.matchedChapterTitle}"
                    } else result.matchedChapterTitle ?: result.matchedUnitNo

                    if (isGrid) {
                        BookGridCard(
                            book = result.book,
                            onClick = { onBookClick(result.book.bookId, result.book.title) },
                            onToggleFavorite = { onToggleFavorite(result.book.bookId, !result.book.isFavorite) },
                            matchedLessonHighlight = highlight
                        )
                    } else {
                        BookListCard(
                            book = result.book,
                            onClick = { onBookClick(result.book.bookId, result.book.title) },
                            onToggleFavorite = { onToggleFavorite(result.book.bookId, !result.book.isFavorite) },
                            matchedLessonHighlight = highlight
                        )
                    }
                }
            } else {
                item(span = { GridItemSpan(spanCount) }) {
                    EmptySearchState(
                        query = state.searchQuery,
                        onClearSearch = onClearSearch
                    )
                }
            }
        } else {
            val noticeText = state.cloudNotice?.takeIf { it.isNotBlank() } ?: "২০২৬ শিক্ষাক্রমের সকল পাঠ্যবই ও সমাধান নিয়মিত হালনাগাদ করা হচ্ছে।"
            item(span = { GridItemSpan(spanCount) }) {
                CloudNoticeBanner(notice = noticeText)
            }

            item(span = { GridItemSpan(spanCount) }) {
                CurriculumSelector(
                    selectedCurriculum = state.selectedCurriculum,
                    onCurriculumSelected = onCurriculumSelected
                )
            }

            state.resumeBook?.let { resume ->
                item(span = { GridItemSpan(spanCount) }) {
                    ResumeReadingSection(
                        book = resume,
                        onClick = { onResumeClick(resume) }
                    )
                }
            }

            item(span = { GridItemSpan(spanCount) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "${state.selectedCurriculum.titleBangla} পাঠ্যবইসমূহ",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        ) {
                            Text(
                                text = "${state.books.size} টি বই",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    if (AppConfig.FEATURE_LAYOUT_SWITCHER) {
                        LayoutSwitchToggle(
                            layoutMode = layoutMode,
                            onToggle = onToggleLayoutMode
                        )
                    }
                }
            }

            items(state.books, key = { it.bookId }) { book ->
                if (isGrid) {
                    BookGridCard(
                        book = book,
                        onClick = { onBookClick(book.bookId, book.title) },
                        onToggleFavorite = { onToggleFavorite(book.bookId, !book.isFavorite) }
                    )
                } else {
                    BookListCard(
                        book = book,
                        onClick = { onBookClick(book.bookId, book.title) },
                        onToggleFavorite = { onToggleFavorite(book.bookId, !book.isFavorite) }
                    )
                }
            }
        }
    }
}
