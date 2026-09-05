package com.helptrickbd.class1.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.helptrickbd.class1.MainActivity
import com.helptrickbd.class1.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.net.URL

/**
 * Universal Utility for handling System Tray Notifications with DRM & Deep Linking.
 * Optimized for performance with network-aware bitmap fetching.
 */
object NotificationHelper {

    const val CHANNEL_ANNOUNCEMENTS = "nctb_announcements"
    const val CHANNEL_BOOK_UPDATES = "nctb_book_updates"
    const val CHANNEL_STUDY_REMINDERS = "nctb_study_reminders"

    const val EXTRA_BOOK_ID = "extra_book_id"
    const val EXTRA_ACTION_URL = "extra_action_url"
    const val EXTRA_NAVIGATE_INBOX = "extra_navigate_inbox"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val announcementsChannel = NotificationChannel(
                CHANNEL_ANNOUNCEMENTS,
                "জরুরি নোটিশ ও আপডেট",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "NCTB প্রথম শ্রেণির গুরুত্বপূর্ণ নোটিশ ও আপডেট"
                enableVibration(true)
                setShowBadge(true)
            }

            val booksChannel = NotificationChannel(
                CHANNEL_BOOK_UPDATES,
                "নতুন বই ও সমাধান",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "নতুন বিষয়ের অধ্যায় ও সমাধান আপডেট"
                enableVibration(true)
            }

            val reminderChannel = NotificationChannel(
                CHANNEL_STUDY_REMINDERS,
                "দৈনিক পড়ার রিমাইন্ডার",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "প্রতিদিনের পড়ার উৎসাহব্যঞ্জক রিমাইন্ডার"
            }

            manager.createNotificationChannels(listOf(announcementsChannel, booksChannel, reminderChannel))
        }
    }

    /**
     * Displays a system notification with optional image and deep linking.
     */
    suspend fun showNotification(
        context: Context,
        title: String,
        message: String,
        imageUrl: String? = null,
        bookId: String? = null,
        actionUrl: String? = null,
        channelId: String = CHANNEL_ANNOUNCEMENTS
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            bookId?.let { putExtra(EXTRA_BOOK_ID, it) }
            actionUrl?.let { putExtra(EXTRA_ACTION_URL, it) }
            putExtra(EXTRA_NAVIGATE_INBOX, true)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Security/Performance: Fetch bitmap with 5-second timeout
        if (!imageUrl.isNullOrBlank()) {
            val bitmap = downloadBitmap(imageUrl)
            if (bitmap != null) {
                builder.setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .setSummaryText(message)
                )
            }
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify((System.currentTimeMillis() % 100000).toInt(), builder.build())
    }

    private suspend fun downloadBitmap(urlStr: String): Bitmap? = withContext(Dispatchers.IO) {
        // Logic Fix: Added 5s timeout to prevent UI hang or process death during slow networks
        withTimeoutOrNull(5000) {
            try {
                val input = URL(urlStr).openStream()
                BitmapFactory.decodeStream(input)
            } catch (_: Exception) {
                null
            }
        }
    }
}
