package com.helptrickbd.class1.feature.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.presentation.HomeUiState
import com.helptrickbd.class1.feature.home.presentation.HomeViewModel
import com.helptrickbd.class1.feature.home.ui.components.HomeBody
import com.helptrickbd.class1.feature.home.ui.components.drawer.AppNavigationDrawer
import kotlinx.coroutines.launch

import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onBookClick: (String, String) -> Unit = { _, _ -> },
    onResumeClick: (Book) -> Unit = { book -> onBookClick(book.bookId, book.title) },
    onNotificationClick: () -> Unit = {}
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
                    navigationIcon = Icons.Rounded.Menu,
                    onNavigationClick = { 
                        coroutineScope.launch { drawerState.open() }
                    },
                    actions = {
                        val unread = (uiState as? HomeUiState.Success)?.unreadNotifications ?: 0
                        IconButton(onClick = onNotificationClick) {
                            BadgedBox(
                                badge = {
                                    if (unread > 0) {
                                        Badge(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            contentColor = MaterialTheme.colorScheme.onPrimary
                                        ) {
                                            Text("$unread", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Notifications,
                                    contentDescription = "নোটিফিকেশন ও নোটিশ",
                                    tint = androidx.compose.ui.graphics.Color.White
                                )
                            }
                        }
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
                        onToggleFavorite = { bookId, isFav -> viewModel.onToggleFavorite(bookId, isFav) },
                        onToggleLayoutMode = viewModel::onToggleLayoutMode
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
