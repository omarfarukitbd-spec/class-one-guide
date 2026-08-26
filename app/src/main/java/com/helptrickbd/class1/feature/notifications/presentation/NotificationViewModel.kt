package com.helptrickbd.class1.feature.notifications.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.core.notification.domain.model.AppNotification
import com.helptrickbd.class1.core.notification.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface NotificationUiState {
    data object Loading : NotificationUiState
    @Immutable
    data class Success(
        val notifications: List<AppNotification>,
        val unreadCount: Int
    ) : NotificationUiState
}

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {

    val uiState: StateFlow<NotificationUiState> = combine(
        repository.getNotifications(),
        repository.getUnreadCount()
    ) { notifications, unreadCount ->
        NotificationUiState.Success(notifications, unreadCount) as NotificationUiState
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NotificationUiState.Loading
    )

    fun markAsRead(id: String) {
        viewModelScope.launch {
            repository.markAsRead(id)
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            repository.markAllAsRead()
        }
    }

    fun deleteNotification(id: String) {
        viewModelScope.launch {
            repository.deleteNotification(id)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }
}
