package com.helptrickbd.class1.core.security

import android.content.Context
import android.os.Build
import android.os.Debug
import com.helptrickbd.class1.core.config.AppConfig
import java.io.File

/**
 * Enterprise Device Integrity & Anti-Tamper Security Manager.
 * Proactively verifies device security posture, root binaries, and hooking frameworks.
 */
object SecurityManager {

    private val ROOT_PATHS = arrayOf(
        "/system/app/Superuser.apk",
        "/sbin/su",
        "/system/bin/su",
        "/system/xbin/su",
        "/data/local/xbin/su",
        "/data/local/bin/su",
        "/system/sd/xbin/su",
        "/system/bin/failsafe/su",
        "/data/local/su",
        "/su/bin/su"
    )

    /**
     * Comprehensive security audit. Returns true if device is completely safe.
     */
    fun isDeviceSecure(context: Context): Boolean {
        if (!AppConfig.ROOT_DETECTION_ENABLED) return true
        return !isRooted() && !isHookingDetected() && !isDebuggerAttached()
    }

    /**
     * Checks for su binary presence and root management packages.
     */
    fun isRooted(): Boolean {
        return checkRootBinaries() || checkBuildTags()
    }

    private fun checkRootBinaries(): Boolean {
        for (path in ROOT_PATHS) {
            try {
                val file = File(path)
                if (file.exists()) return true
            } catch (_: Exception) {
                // Ignore security exceptions
            }
        }
        return false
    }

    private fun checkBuildTags(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    /**
     * Detects Frida and dynamic instrumentation hooks.
     */
    fun isHookingDetected(): Boolean {
        if (!AppConfig.FRIDA_TAMPER_PROTECTION_ENABLED) return false
        try {
            val mapsFile = File("/proc/self/maps")
            if (mapsFile.exists()) {
                val content = mapsFile.readText()
                if (content.contains("frida") || content.contains("xposed") || content.contains("substrate")) {
                    return true
                }
            }
        } catch (_: Exception) {
            // Ignored
        }
        return false
    }

    /**
     * Checks if third-party active debugger is attached to intercept RAM.
     */
    fun isDebuggerAttached(): Boolean {
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger()
    }
}
