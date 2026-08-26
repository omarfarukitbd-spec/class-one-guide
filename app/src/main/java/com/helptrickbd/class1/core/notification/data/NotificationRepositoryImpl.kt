package com.helptrickbd.class1.core.notification.data

import com.helptrickbd.class1.core.database.NotificationDao
import com.helptrickbd.class1.core.database.NotificationEntity
import com.helptrickbd.class1.core.di.IoDispatcher
import com.helptrickbd.class1.core.notification.domain.model.AppNotification
import com.helptrickbd.class1.core.notification.domain.repository.NotificationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NotificationRepository {

    override fun getNotifications(): Flow<List<AppNotification>> {
        return notificationDao.getAllNotifications().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getUnreadCount(): Flow<Int> = notificationDao.getUnreadCount()

    override suspend fun saveNotification(notification: AppNotification) = withContext(ioDispatcher) {
        notificationDao.insertNotification(notification.toEntity())
    }

    override suspend fun markAsRead(id: String) = withContext(ioDispatcher) {
        notificationDao.markAsRead(id)
    }

    override suspend fun markAllAsRead() = withContext(ioDispatcher) {
        notificationDao.markAllAsRead()
    }

    override suspend fun deleteNotification(id: String) = withContext(ioDispatcher) {
        notificationDao.deleteNotification(id)
    }

    override suspend fun clearAll() = withContext(ioDispatcher) {
        notificationDao.clearAllNotifications()
    }

    private fun NotificationEntity.toDomain(): AppNotification = AppNotification(
        id = id,
        title = title,
        message = message,
        imageUrl = imageUrl,
        bookId = bookId,
        actionUrl = actionUrl,
        isRead = isRead,
        timestamp = timestamp
    )

    private fun AppNotification.toEntity(): NotificationEntity = NotificationEntity(
        id = id,
        title = title,
        message = message,
        imageUrl = imageUrl,
        bookId = bookId,
        actionUrl = actionUrl,
        isRead = isRead,
        timestamp = timestamp
    )
}
