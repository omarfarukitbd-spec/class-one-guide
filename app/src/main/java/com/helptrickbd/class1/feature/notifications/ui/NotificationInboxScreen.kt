package com.helptrickbd.class1.feature.notifications.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.notifications.presentation.NotificationUiState
import com.helptrickbd.class1.feature.notifications.presentation.NotificationViewModel
import com.helptrickbd.class1.feature.notifications.ui.components.NotificationItemCard

@Composable
fun NotificationInboxScreen(
    viewModel: NotificationViewModel,
    onBackClick: () -> Unit,
    onBookClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            StandardTopBar(
                title = "নোটিফিকেশন ও নোটিশ",
                subtitle = "সর্বশেষ আপডেট ও তথ্যাবলী",
                navigationIcon = Icons.AutoMirrored.Rounded.ArrowBack,
                onNavigationClick = onBackClick,
                actions = {
                    val count = (uiState as? NotificationUiState.Success)?.unreadCount ?: 0
                    if (count > 0) {
                        IconButton(onClick = viewModel::markAllAsRead) {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircleOutline,
                                contentDescription = "সব পঠিত করুন",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when (val state = uiState) {
            is NotificationUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is NotificationUiState.Success -> {
                if (state.notifications.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.NotificationsOff,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(56.dp)
                            )
                            Text(
                                text = "কোনো নতুন নোটিশ নেই",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            top = innerPadding.calculateTopPadding() + 12.dp,
                            bottom = innerPadding.calculateBottomPadding() + 20.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.notifications, key = { it.id }) { notification ->
                            NotificationItemCard(
                                notification = notification,
                                onClick = {
                                    viewModel.markAsRead(notification.id)
                                    notification.bookId?.let { onBookClick(it) }
                                },
                                onDelete = { viewModel.deleteNotification(notification.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
