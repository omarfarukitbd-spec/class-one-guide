package com.helptrickbd.class1.core.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.helptrickbd.class1.core.config.AppConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DailyReminderReceiver : BroadcastReceiver() {

    private val receiverScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onReceive(context: Context, intent: Intent?) {
        if (!AppConfig.FEATURE_DAILY_STUDY_REMINDER) return

        val pendingResult = goAsync()

        receiverScope.launch {
            try {
                val title = "পড়ার সময় হয়েছে! 📚"
                val message = "আজকের পড়া শেষ হয়েছে তো? চলো ১ম শ্রেণির মজার পড়াগুলো একটু দেখে নিই!"

                NotificationHelper.showNotification(
                    context = context,
                    title = title,
                    message = message,
                    channelId = NotificationHelper.CHANNEL_STUDY_REMINDERS
                )
            } finally {
                pendingResult.finish()
                // Reschedule for next day after the notification is shown
                DailyStudyReminderScheduler.scheduleDailyReminder(context)
            }
        }
    }
}
