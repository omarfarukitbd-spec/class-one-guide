package com.helptrickbd.class1.core.analytics.data

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.helptrickbd.class1.core.analytics.domain.AnalyticsTracker
import com.helptrickbd.class1.core.config.AppConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsTrackerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AnalyticsTracker {

    private val firebaseAnalytics: FirebaseAnalytics? by lazy {
        if (AppConfig.FEATURE_ANALYTICS) {
            try {
                FirebaseAnalytics.getInstance(context)
            } catch (_: Exception) {
                null
            }
        } else null
    }

    override fun logEvent(name: String, params: Map<String, Any>) {
        if (!AppConfig.FEATURE_ANALYTICS) return
        try {
            val bundle = Bundle().apply {
                params.forEach { (key, value) ->
                    when (value) {
                        is String -> putString(key, value)
                        is Int -> putInt(key, value)
                        is Long -> putLong(key, value)
                        is Double -> putDouble(key, value)
                        is Boolean -> putBoolean(key, value)
                        else -> putString(key, value.toString())
                    }
                }
            }
            firebaseAnalytics?.logEvent(name, bundle)
        } catch (_: Exception) {
        }
    }

    override fun logScreenView(screenName: String, screenClass: String?) {
        if (!AppConfig.FEATURE_ANALYTICS) return
        try {
            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass ?: screenName)
            }
            firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        } catch (_: Exception) {
        }
    }

    override fun setUserProperty(name: String, value: String) {
        if (!AppConfig.FEATURE_ANALYTICS) return
        try {
            firebaseAnalytics?.setUserProperty(name, value)
        } catch (_: Exception) {
        }
    }
}
