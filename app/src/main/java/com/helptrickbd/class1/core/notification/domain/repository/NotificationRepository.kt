package com.helptrickbd.class1.core.notification.domain.repository

import com.helptrickbd.class1.core.notification.domain.model.AppNotification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications(): Flow<List<AppNotification>>
    fun getUnreadCount(): Flow<Int>
    suspend fun saveNotification(notification: AppNotification)
    suspend fun markAsRead(id: String)
    suspend fun markAllAsRead()
    suspend fun deleteNotification(id: String)
    suspend fun clearAll()
}
