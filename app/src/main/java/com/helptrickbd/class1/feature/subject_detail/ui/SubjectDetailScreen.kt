package com.helptrickbd.class1.feature.subject_detail.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.R
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
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SubjectDetailUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            val book = (uiState as? SubjectDetailUiState.Success)?.book
            val title = book?.title ?: stringResource(R.string.title_subject_detail)
            val isFav = book?.isFavorite == true

            StandardTopBar(
                title = title,
                subtitle = stringResource(R.string.subtitle_subject_detail),
                navigationIcon = Icons.AutoMirrored.Rounded.ArrowBack,
                onNavigationClick = onBackClick,
                actions = {
                    if (book != null) {
                        IconButton(onClick = viewModel::onToggleFavorite) {
                            Icon(
                                imageVector = if (isFav) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                                contentDescription = if (isFav) {
                                    stringResource(R.string.desc_remove_bookmark)
                                } else {
                                    stringResource(R.string.desc_add_bookmark)
                                },
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
                FullScreenMessage(
                    innerPadding = innerPadding,
                    message = state.message.asString(),
                    icon = Icons.Rounded.Refresh,
                    buttonText = stringResource(R.string.btn_retry),
                    onButtonClick = viewModel::loadBookDetails
                )
            }
            is SubjectDetailUiState.Empty -> {
                FullScreenMessage(
                    innerPadding = innerPadding,
                    message = state.message.asString(),
                    icon = Icons.Rounded.SearchOff
                )
            }
        }
    }
}

@Composable
private fun FullScreenMessage(
    innerPadding: PaddingValues,
    message: String,
    icon: ImageVector,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            if (buttonText != null && onButtonClick != null) {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onButtonClick,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = buttonText)
                }
            }
        }
    }
}
