package com.helptrickbd.class1.core.analytics.data

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.helptrickbd.class1.core.analytics.domain.CrashReporter
import com.helptrickbd.class1.core.config.AppConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseCrashReporterImpl @Inject constructor() : CrashReporter {

    private val crashlytics: FirebaseCrashlytics? by lazy {
        if (AppConfig.FEATURE_CRASHLYTICS) {
            try {
                FirebaseCrashlytics.getInstance().apply {
                    setCrashlyticsCollectionEnabled(true)
                }
            } catch (_: Exception) {
                null
            }
        } else null
    }

    override fun recordException(throwable: Throwable) {
        if (!AppConfig.FEATURE_CRASHLYTICS) return
        try {
            crashlytics?.recordException(throwable)
        } catch (_: Exception) {
        }
    }

    override fun log(message: String) {
        if (!AppConfig.FEATURE_CRASHLYTICS) return
        try {
            crashlytics?.log(message)
        } catch (_: Exception) {
        }
    }

    override fun setCustomKey(key: String, value: String) {
        if (!AppConfig.FEATURE_CRASHLYTICS) return
        try {
            crashlytics?.setCustomKey(key, value)
        } catch (_: Exception) {
        }
    }

    override fun setCustomKey(key: String, value: Boolean) {
        if (!AppConfig.FEATURE_CRASHLYTICS) return
        try {
            crashlytics?.setCustomKey(key, value)
        } catch (_: Exception) {
        }
    }

    override fun setCustomKey(key: String, value: Int) {
        if (!AppConfig.FEATURE_CRASHLYTICS) return
        try {
            crashlytics?.setCustomKey(key, value)
        } catch (_: Exception) {
        }
    }

    override fun setUserId(userId: String) {
        if (!AppConfig.FEATURE_CRASHLYTICS) return
        try {
            crashlytics?.setUserId(userId)
        } catch (_: Exception) {
        }
    }
}
