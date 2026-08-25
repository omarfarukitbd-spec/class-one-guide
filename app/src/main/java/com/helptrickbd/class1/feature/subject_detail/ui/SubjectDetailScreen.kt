package com.helptrickbd.class1.feature.subject_detail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.home.domain.model.Resource
import com.helptrickbd.class1.feature.subject_detail.ui.components.SubjectDetailContent

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
                navigationIcon = Icons.AutoMirrored.Rounded.ArrowBack,
                onNavigationClick = onBackClick,
                actions = {
                    if (book != null) {
                        IconButton(onClick = viewModel::onToggleFavorite) {
                            Icon(
                                imageVector = if (isFav) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
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
                SubjectDetailContent(
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
