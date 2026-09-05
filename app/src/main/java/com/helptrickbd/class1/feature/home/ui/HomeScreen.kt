package com.helptrickbd.class1.feature.home.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.presentation.HomeUiEvent
import com.helptrickbd.class1.feature.home.presentation.HomeUiState
import com.helptrickbd.class1.feature.home.presentation.HomeViewModel
import com.helptrickbd.class1.feature.home.ui.components.FlexibleUpdateDialog
import com.helptrickbd.class1.feature.home.ui.components.HomeBody
import com.helptrickbd.class1.feature.home.ui.components.drawer.AppNavigationDrawer
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onBookClick: (String, String) -> Unit = { _, _ -> },
    onResumeClick: (Book) -> Unit = { book -> onBookClick(book.bookId, book.title) },
    onNotificationClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val settingsState by viewModel.settingsState.collectAsStateWithLifecycle()
    val showUpdateDialog by viewModel.showUpdateDialog.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HomeUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    val hasActiveSearch = (uiState as? HomeUiState.Success)?.searchQuery?.isNotBlank() == true
    BackHandler(enabled = hasActiveSearch) {
        viewModel.onClearSearch()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppNavigationDrawer(
                onOfflineBooksClick = { Toast.makeText(context, context.getString(R.string.msg_offline_books_coming_soon), Toast.LENGTH_SHORT).show() },
                onCheckUpdateClick = { Toast.makeText(context, context.getString(R.string.msg_app_up_to_date), Toast.LENGTH_SHORT).show() },
                onFbGroupClick = { Toast.makeText(context, context.getString(R.string.msg_facebook_group_link), Toast.LENGTH_SHORT).show() },
                onShareClick = { Toast.makeText(context, context.getString(R.string.msg_share_coming_soon), Toast.LENGTH_SHORT).show() },
                onRateClick = { Toast.makeText(context, context.getString(R.string.msg_play_store_link), Toast.LENGTH_SHORT).show() },
                onPrivacyClick = { Toast.makeText(context, context.getString(R.string.msg_privacy_policy), Toast.LENGTH_SHORT).show() },
                onAboutClick = { Toast.makeText(context, context.getString(R.string.msg_developer_info), Toast.LENGTH_SHORT).show() },
                onCloseDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = { 
                StandardTopBar(
                    title = stringResource(R.string.title_home),
                    subtitle = stringResource(R.string.subtitle_home),
                    navigationIcon = Icons.Rounded.Menu,
                    onNavigationClick = { 
                        coroutineScope.launch { drawerState.open() }
                    },
                    actions = {
                        val unread = settingsState.unreadNotifications
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
                                    contentDescription = stringResource(R.string.desc_notifications),
                                    tint = Color.White
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
                        layoutMode = settingsState.layoutMode,
                        onSearchQueryChange = viewModel::onSearchQueryChange,
                        onClearSearch = viewModel::onClearSearch,
                        onCurriculumSelected = viewModel::onCurriculumSelected,
                        onBookClick = onBookClick,
                        onResumeClick = onResumeClick,
                        onToggleFavorite = { bookId, isFav -> viewModel.onToggleFavorite(bookId, isFav) },
                        onToggleLayoutMode = viewModel::onToggleLayoutMode
                    )
                    
                    if (showUpdateDialog) {
                        FlexibleUpdateDialog(
                            onDismiss = viewModel::onDismissUpdateDialog
                        )
                    }
                }
                is HomeUiState.Error -> {
                    FullScreenError(
                        innerPadding = innerPadding,
                        message = state.message.asString(),
                        onRetry = { /* Sync is automatic via NetworkMonitor, but could add manual trigger */ }
                    )
                }
                is HomeUiState.Empty -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message.asString(), 
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FullScreenError(
    innerPadding: PaddingValues,
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message, 
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text(stringResource(R.string.btn_retry))
            }
        }
    }
}
