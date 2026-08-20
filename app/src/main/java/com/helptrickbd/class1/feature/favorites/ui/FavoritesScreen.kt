package com.helptrickbd.class1.feature.favorites.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.favorites.presentation.FavoritesUiState
import com.helptrickbd.class1.feature.favorites.presentation.FavoritesViewModel
import com.helptrickbd.class1.feature.home.ui.components.BookGridCard

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onBookClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            StandardTopBar(
                title = "পছন্দের বই ও বুকমার্ক",
                subtitle = "আপনার সংরক্ষিত বইয়ের দ্রুত তালিকা"
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
                        top = innerPadding.calculateTopPadding() + 16.dp,
                        bottom = innerPadding.calculateBottomPadding() + 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.books, key = { it.bookId }) { book ->
                        BookGridCard(
                            book = book,
                            onClick = { onBookClick(book.bookId, book.title) },
                            onToggleFavorite = { viewModel.onToggleFavorite(book.bookId, !book.isFavorite) }
                        )
                    }
                }
            }
            is FavoritesUiState.Empty -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "কোনো বই বুকমার্ক করা নেই",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "পছন্দের বইগুলো বুকমার্ক করে রাখলে এখানে খুব দ্রুত এক ক্লিকে পড়তে পারবেন।",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp
                    )
                }
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
