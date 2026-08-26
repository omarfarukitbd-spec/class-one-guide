package com.helptrickbd.class1.core.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.helptrickbd.class1.core.config.AppConfig
import kotlinx.coroutines.tasks.await

object FcmTopicManager {

    suspend fun subscribeDefaultTopics() {
        if (!AppConfig.FEATURE_PUSH_NOTIFICATIONS) return

        try {
            val messaging = FirebaseMessaging.getInstance()
            messaging.subscribeToTopic(AppConfig.FCM_TOPIC_CLASS).await()
            messaging.subscribeToTopic(AppConfig.FCM_TOPIC_GLOBAL).await()
        } catch (_: Exception) {
            // Graceful fallback for offline mode
        }
    }

    suspend fun subscribeToTopic(topic: String): Boolean {
        return try {
            FirebaseMessaging.getInstance().subscribeToTopic(topic).await()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun unsubscribeFromTopic(topic: String): Boolean {
        return try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).await()
            true
        } catch (_: Exception) {
            false
        }
    }
}
