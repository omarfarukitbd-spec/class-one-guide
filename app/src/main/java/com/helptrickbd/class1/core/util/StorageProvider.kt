package com.helptrickbd.class1.core.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

interface StorageProvider {
    val cacheDir: File
    suspend fun runMaintenanceCleanup()
}

@Singleton
class StorageProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StorageProvider {
    override val cacheDir: File
        get() = context.cacheDir

    /**
     * Cleans up stale temporary files and secure session leftovers.
     * Logic: Only removes files older than 24 hours to avoid interrupting active sessions.
     */
    override suspend fun runMaintenanceCleanup() {
        val cache = context.cacheDir
        val currentTime = System.currentTimeMillis()
        val expiryTime = 24 * 60 * 60 * 1000 // 24 Hours

        if (cache.exists() && cache.isDirectory) {
            // 1. Clean secure session temp files from leaks or past crashes
            cache.listFiles()?.filter { 
                (it.name.startsWith("sec_sess_") || it.name.startsWith("secure_session_")) && 
                currentTime - it.lastModified() > expiryTime 
            }?.forEach { it.delete() }

            // 2. Clean unfinished or corrupted downloads
            val pdfDir = File(cache, "pdfs")
            if (pdfDir.exists()) {
                pdfDir.listFiles()?.filter { 
                    it.name.endsWith(".tmp") && currentTime - it.lastModified() > expiryTime 
                }?.forEach { it.delete() }
            }
        }
    }
}
