package com.helptrickbd.class1.feature.learning.ui.slate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.learning.domain.model.DrawingPath
import com.helptrickbd.class1.feature.learning.domain.model.LearningCategory
import com.helptrickbd.class1.feature.learning.domain.model.LearningItem
import com.helptrickbd.class1.feature.learning.presentation.LearningUiState
import com.helptrickbd.class1.feature.learning.presentation.LearningViewModel
import com.helptrickbd.class1.feature.learning.ui.components.LearningCanvas

@Composable
fun AlphabetSlateScreen(
    onBackClick: () -> Unit,
    viewModel: LearningViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            StandardTopBar(
                title = "বর্ণমালা ও সংখ্যা স্লেট",
                subtitle = "হাতে-কলমে লেখার সহজ অনুশীলন",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationClick = onBackClick
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is LearningUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is LearningUiState.Success -> {
                SlateContent(
                    innerPadding = innerPadding,
                    state = state,
                    onCategorySelected = viewModel::selectCategory,
                    onItemSelected = viewModel::selectItem,
                    onPathDrawn = viewModel::addPath,
                    onClear = viewModel::clearCanvas
                )
            }
            is LearningUiState.Error -> {
                Text(text = state.message)
            }
        }
    }
}

@Composable
private fun SlateContent(
    innerPadding: PaddingValues,
    state: LearningUiState.Success,
    onCategorySelected: (LearningCategory) -> Unit,
    onItemSelected: (LearningItem) -> Unit,
    onPathDrawn: (DrawingPath) -> Unit,
    onClear: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        ScrollableTabRow(
            selectedTabIndex = state.categories.indexOf(state.selectedCategory),
            edgePadding = 16.dp,
            containerColor = Color.Transparent,
            divider = {}
        ) {
            state.categories.filter { it != LearningCategory.FREE_DRAW }.forEach { category ->
                Tab(
                    selected = state.selectedCategory == category,
                    onClick = { onCategorySelected(category) },
                    text = { Text(category.title) }
                )
            }
        }

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(state.items, key = { it.id }) { item ->
                val isSelected = state.selectedItem?.id == item.id
                Surface(
                    onClick = { onItemSelected(item) },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = item.character,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            state.selectedItem?.let { item ->
                Text(
                    text = item.character,
                    fontSize = 220.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            LearningCanvas(
                paths = state.paths,
                onPathDrawn = onPathDrawn,
                currentColor = state.selectedColor,
                strokeWidth = if (state.isEraser) 40f else 16f
            )
            
            Button(
                onClick = onClear,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("মুছে ফেলুন")
            }
        }
    }
}
