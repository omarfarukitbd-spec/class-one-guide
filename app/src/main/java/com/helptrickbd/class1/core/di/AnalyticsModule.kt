package com.helptrickbd.class1.core.di

import com.helptrickbd.class1.core.analytics.data.FirebaseAnalyticsTrackerImpl
import com.helptrickbd.class1.core.analytics.data.FirebaseCrashReporterImpl
import com.helptrickbd.class1.core.analytics.domain.AnalyticsTracker
import com.helptrickbd.class1.core.analytics.domain.CrashReporter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    @Singleton
    abstract fun bindAnalyticsTracker(
        impl: FirebaseAnalyticsTrackerImpl
    ): AnalyticsTracker

    @Binds
    @Singleton
    abstract fun bindCrashReporter(
        impl: FirebaseCrashReporterImpl
    ): CrashReporter
}
