package com.helptrickbd.class1.core.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.helptrickbd.class1.core.config.AppConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (!AppConfig.FEATURE_DAILY_STUDY_REMINDER) return

        val title = "পড়ার সময় হয়েছে! 📚"
        val message = "আজকের পড়া শেষ হয়েছে তো? চলো ১ম শ্রেণির মজার পড়াগুলো একটু দেখে নিই!"

        CoroutineScope(Dispatchers.IO).launch {
            NotificationHelper.showNotification(
                context = context,
                title = title,
                message = message,
                channelId = NotificationHelper.CHANNEL_STUDY_REMINDERS
            )
        }

        // Reschedule for next day
        DailyStudyReminderScheduler.scheduleDailyReminder(context)
    }
}
