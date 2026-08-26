package com.helptrickbd.class1.core.notification.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val imageUrl: String? = null,
    val bookId: String? = null,
    val actionUrl: String? = null,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
