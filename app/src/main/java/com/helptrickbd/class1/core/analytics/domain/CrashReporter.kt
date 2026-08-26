package com.helptrickbd.class1.core.analytics.domain

/**
 * Clean Architecture Interface for logging non-fatal exceptions and custom keys to Crashlytics.
 */
interface CrashReporter {
    fun recordException(throwable: Throwable)
    fun log(message: String)
    fun setCustomKey(key: String, value: String)
    fun setCustomKey(key: String, value: Boolean)
    fun setCustomKey(key: String, value: Int)
    fun setUserId(userId: String)
}
