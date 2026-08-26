package com.helptrickbd.class1.core.analytics.domain

/**
 * Clean Architecture Interface for logging user events and screen views.
 */
interface AnalyticsTracker {
    fun logEvent(name: String, params: Map<String, Any> = emptyMap())
    fun logScreenView(screenName: String, screenClass: String? = null)
    fun setUserProperty(name: String, value: String)
}
