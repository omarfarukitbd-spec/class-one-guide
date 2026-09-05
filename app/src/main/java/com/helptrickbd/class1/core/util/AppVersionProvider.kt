package com.helptrickbd.class1.core.util

import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface AppVersionProvider {
    val currentVersionCode: Long
}

@Singleton
class AppVersionProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppVersionProvider {
    override val currentVersionCode: Long
        get() = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                context.packageManager.getPackageInfo(context.packageName, 0).longVersionCode
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(context.packageName, 0).versionCode.toLong()
            }
        } catch (e: Exception) {
            1L
        }
}
