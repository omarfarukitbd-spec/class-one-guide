package com.helptrickbd.class1

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.notification.DailyStudyReminderScheduler
import com.helptrickbd.class1.core.notification.FcmTopicManager
import com.helptrickbd.class1.core.notification.NotificationHelper
import com.helptrickbd.class1.core.security.SecurityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class Class1App : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // 1. Proactive Integrity & Anti-Hooking Shield
        if (AppConfig.ROOT_DETECTION_ENABLED || AppConfig.FRIDA_TAMPER_PROTECTION_ENABLED) {
            SecurityManager.isDeviceSecure(this)
        }

        // 2. Firebase App Initialization
        try {
            FirebaseApp.initializeApp(this)
        } catch (_: Exception) {
        }

        // 3. Firebase Crashlytics Metadata Tagging
        if (AppConfig.FEATURE_CRASHLYTICS) {
            try {
                val crashlytics = com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance()
                crashlytics.setCrashlyticsCollectionEnabled(true)
                crashlytics.setCustomKey("class_id", AppConfig.TARGET_CLASS_ID)
                crashlytics.setCustomKey("academic_year", AppConfig.ACADEMIC_YEAR)
                crashlytics.setCustomKey("app_name", AppConfig.APP_NAME)
                crashlytics.setCustomKey("default_curriculum", AppConfig.DEFAULT_CURRICULUM.name)
            } catch (_: Exception) {
            }
        }

        // 4. Notification Engine Initialization
        if (AppConfig.FEATURE_PUSH_NOTIFICATIONS) {
            NotificationHelper.createNotificationChannels(this)
            DailyStudyReminderScheduler.scheduleDailyReminder(this)
            CoroutineScope(Dispatchers.IO).launch {
                FcmTopicManager.subscribeDefaultTopics()
            }
        }
    }
}
