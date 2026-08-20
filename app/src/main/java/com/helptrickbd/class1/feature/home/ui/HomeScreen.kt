package com.helptrickbd.class1.feature.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.presentation.HomeUiState
import com.helptrickbd.class1.feature.home.presentation.HomeViewModel
import com.helptrickbd.class1.feature.home.ui.components.BookGridCard
import com.helptrickbd.class1.feature.home.ui.components.CurriculumSelector
import com.helptrickbd.class1.feature.home.ui.components.ResumeReadingSection

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onBookClick: (String, String) -> Unit = { _, _ -> }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { 
            StandardTopBar(
                title = "প্রথম শ্রেণির গাইড ও পাঠ্যবই",
                subtitle = "জাতীয় শিক্ষাক্রম ও পাঠ্যপুস্তক বোর্ড (NCTB)",
                navigationIcon = Icons.Default.Menu,
                onNavigationClick = { /* Handle Drawer */ }
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is HomeUiState.Success -> {
                HomeBody(
                    innerPadding = innerPadding,
                    state = state,
                    onCurriculumSelected = viewModel::onCurriculumSelected,
                    onBookClick = onBookClick
                )
            }
            is HomeUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
private fun HomeBody(
    innerPadding: PaddingValues,
    state: HomeUiState.Success,
    onCurriculumSelected: (com.helptrickbd.class1.feature.home.domain.model.Curriculum) -> Unit,
    onBookClick: (String, String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = innerPadding.calculateTopPadding() + 16.dp,
            bottom = innerPadding.calculateBottomPadding() + 24.dp,
            start = 16.dp,
            end = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
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
                    onClick = { onBookClick(resume.bookId, resume.title) }
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
                onClick = { onBookClick(book.bookId, book.title) }
            )
        }
    }
}
