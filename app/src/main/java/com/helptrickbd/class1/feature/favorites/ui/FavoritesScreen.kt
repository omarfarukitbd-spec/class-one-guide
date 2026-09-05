package com.helptrickbd.class1.feature.favorites.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.favorites.presentation.FavoritesUiState
import com.helptrickbd.class1.feature.favorites.presentation.FavoritesViewModel
import com.helptrickbd.class1.feature.favorites.ui.components.FavoritesEmptyState
import com.helptrickbd.class1.feature.favorites.ui.components.RecentReadingHeroCard
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.ui.components.BookGridCard

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onBookClick: (String, String) -> Unit,
    onResumeClick: (Book) -> Unit = { book -> onBookClick(book.bookId, book.title) },
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            StandardTopBar(
                title = "পছন্দের বই ও বুকমার্ক",
                subtitle = "আপনার সংরক্ষিত বই ও পড়ার ধারাবাহিকতা",
                navigationIcon = if (onBackClick != null) Icons.AutoMirrored.Rounded.ArrowBack else null,
                onNavigationClick = onBackClick ?: {}
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is FavoritesUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is FavoritesUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding() + 14.dp,
                        bottom = innerPadding.calculateBottomPadding() + 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    // 1. Recent Reading Hero Banner
                    if (state.recentBook != null) {
                        item(span = { GridItemSpan(2) }) {
                            RecentReadingHeroCard(
                                book = state.recentBook,
                                onClick = { onResumeClick(state.recentBook) }
                            )
                        }
                    }

                    // 2. Bookmarks Header
                    if (state.favoriteBooks.isNotEmpty()) {
                        item(span = { GridItemSpan(2) }) {
                            Text(
                                text = "বুকমার্ক করা বইসমূহ (${state.favoriteBooks.size}টি)",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        // 3. Bookmarks Grid
                        items(state.favoriteBooks, key = { it.bookId }) { book ->
                            BookGridCard(
                                book = book,
                                onClick = { onBookClick(book.bookId, book.title) },
                                onToggleFavorite = { viewModel.onToggleFavorite(book.bookId, !book.isFavorite) }
                            )
                        }
                    }
                }
            }
            is FavoritesUiState.Empty -> {
                FavoritesEmptyState(modifier = Modifier.padding(innerPadding))
            }
            is FavoritesUiState.Error -> {
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
