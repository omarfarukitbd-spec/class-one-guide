package com.helptrickbd.class1.feature.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.designsystem.components.AppSearchBar
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.presentation.HomeUiState
import com.helptrickbd.class1.feature.home.presentation.HomeViewModel
import com.helptrickbd.class1.feature.home.ui.components.BookGridCard
import com.helptrickbd.class1.feature.home.ui.components.CurriculumSelector
import com.helptrickbd.class1.feature.home.ui.components.ResumeReadingSection
import com.helptrickbd.class1.feature.home.ui.components.drawer.AppNavigationDrawer
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onBookClick: (String, String) -> Unit = { _, _ -> },
    onResumeClick: (Book) -> Unit = { book -> onBookClick(book.bookId, book.title) }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val currentState = uiState
    val storageInfo = (currentState as? HomeUiState.Success)?.storageInfo ?: com.helptrickbd.class1.core.settings.domain.model.StorageInfo()
    val themeMode = (currentState as? HomeUiState.Success)?.themeMode ?: com.helptrickbd.class1.core.settings.domain.model.ThemeMode.SYSTEM
    val selectedCurriculum = (currentState as? HomeUiState.Success)?.selectedCurriculum ?: AppConfig.DEFAULT_CURRICULUM

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppNavigationDrawer(
                storageInfo = storageInfo,
                selectedTheme = themeMode,
                selectedCurriculum = selectedCurriculum,
                onCurriculumSelected = {
                    viewModel.onCurriculumSelected(it)
                    coroutineScope.launch { drawerState.close() }
                },
                onThemeSelected = viewModel::onThemeSelected,
                onClearCache = viewModel::onClearCache,
                onCloseDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = { 
                StandardTopBar(
                    title = "প্রথম শ্রেণির গাইড ও পাঠ্যবই",
                    subtitle = "জাতীয় শিক্ষাক্রম ও পাঠ্যপুস্তক বোর্ড (NCTB)",
                    navigationIcon = Icons.Default.Menu,
                    onNavigationClick = { 
                        coroutineScope.launch { drawerState.open() }
                    }
                )
            }
        ) { innerPadding ->
            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                is HomeUiState.Success -> {
                    HomeBody(
                        innerPadding = innerPadding,
                        state = state,
                        onSearchQueryChange = viewModel::onSearchQueryChange,
                        onClearSearch = viewModel::onClearSearch,
                        onCurriculumSelected = viewModel::onCurriculumSelected,
                        onBookClick = onBookClick,
                        onResumeClick = onResumeClick,
                        onToggleFavorite = { bookId, isFav -> viewModel.onToggleFavorite(bookId, isFav) }
                    )
                }
                is HomeUiState.Error -> {
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
}

@Composable
private fun HomeBody(
    innerPadding: PaddingValues,
    state: HomeUiState.Success,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onCurriculumSelected: (Curriculum) -> Unit,
    onBookClick: (String, String) -> Unit,
    onResumeClick: (Book) -> Unit,
    onToggleFavorite: (String, Boolean) -> Unit
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
        modifier = Modifier.fillMaxSize()
    ) {
        if (AppConfig.FEATURE_SEARCH) {
            item(span = { GridItemSpan(2) }) {
                AppSearchBar(
                    query = state.searchQuery,
                    onQueryChange = onSearchQueryChange,
                    onClearQuery = onClearSearch
                )
            }
        }

        if (isSearching) {
            if (state.searchResults.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = "অনুসন্ধানের ফলাফল (${state.searchResults.size} টি)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(state.searchResults, key = { it.book.bookId }) { result ->
                    val highlight = if (result.matchedUnitNo != null && result.matchedChapterTitle != null) {
                        "${result.matchedUnitNo} - ${result.matchedChapterTitle}"
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

@Composable
private fun EmptySearchState(
    query: String,
    onClearSearch: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 36.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "'$query' দিয়ে কোনো বই বা অধ্যায় মেলেনি",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "বানান ঠিক আছে কিনা যাচাই করুন অথবা অন্য কোনো শব্দ দিয়ে খুঁজুন।",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        FilledTonalButton(onClick = onClearSearch) {
            Text(text = "সব বই দেখুন")
        }
    }
}
