package com.helptrickbd.class1.core.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.helptrickbd.class1.core.notification.domain.model.AppNotification
import com.helptrickbd.class1.core.notification.domain.repository.NotificationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class AppFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        serviceScope.launch {
            FcmTopicManager.subscribeDefaultTopics()
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title
            ?: remoteMessage.data["title"]
            ?: "NCTB প্রথম শ্রেণি নোটিশ"

        val body = remoteMessage.notification?.body
            ?: remoteMessage.data["body"]
            ?: remoteMessage.data["message"]
            ?: "নতুন পাঠ্যবই বা সমাধান আপডেট করা হয়েছে।"

        val imageUrl = remoteMessage.notification?.imageUrl?.toString()
            ?: remoteMessage.data["imageUrl"]

        val bookId = remoteMessage.data["bookId"]
        val actionUrl = remoteMessage.data["actionUrl"]
        val channelId = remoteMessage.data["channelId"] ?: NotificationHelper.CHANNEL_ANNOUNCEMENTS

        val notificationId = UUID.randomUUID().toString()

        val appNotification = AppNotification(
            id = notificationId,
            title = title,
            message = body,
            imageUrl = imageUrl,
            bookId = bookId,
            actionUrl = actionUrl,
            isRead = false,
            timestamp = System.currentTimeMillis()
        )

        serviceScope.launch {
            // 1. Save to Room Database SSOT (In-App Inbox)
            notificationRepository.saveNotification(appNotification)

            // 2. Display System Notification / Heads-Up Banner
            NotificationHelper.showNotification(
                context = applicationContext,
                title = title,
                message = body,
                imageUrl = imageUrl,
                bookId = bookId,
                actionUrl = actionUrl,
                channelId = channelId
            )
        }
    }
}
