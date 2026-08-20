package com.helptrickbd.class1.core.settings.data

import android.content.Context
import com.helptrickbd.class1.core.settings.domain.model.StorageInfo
import com.helptrickbd.class1.core.settings.domain.model.ThemeMode
import com.helptrickbd.class1.core.settings.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val prefs by lazy {
        context.getSharedPreferences("class1_settings_prefs", Context.MODE_PRIVATE)
    }

    private val themeState = MutableStateFlow(loadInitialTheme())
    private val storageState = MutableStateFlow(calculateStorage())

    private val pdfCacheDir: File
        get() = File(context.cacheDir, "pdfs")

    override fun getStorageInfo(): Flow<StorageInfo> = storageState.asStateFlow()

    override suspend fun clearPdfCache(): Boolean = withContext(Dispatchers.IO) {
        try {
            if (pdfCacheDir.exists()) {
                val files = pdfCacheDir.listFiles() ?: emptyArray()
                files.forEach { it.delete() }
            }
            storageState.value = calculateStorage()
            true
        } catch (_: Exception) {
            false
        }
    }

    override fun getThemeMode(): Flow<ThemeMode> = themeState.asStateFlow()

    override suspend fun setThemeMode(mode: ThemeMode) = withContext(Dispatchers.IO) {
        prefs.edit().putString("saved_theme_mode", mode.name).apply()
        themeState.value = mode
    }

    private fun loadInitialTheme(): ThemeMode {
        val saved = prefs.getString("saved_theme_mode", ThemeMode.SYSTEM.name)
        return runCatching { ThemeMode.valueOf(saved ?: "") }.getOrDefault(ThemeMode.SYSTEM)
    }

    private fun calculateStorage(): StorageInfo {
        if (!pdfCacheDir.exists()) return StorageInfo()

        val files = pdfCacheDir.listFiles() ?: emptyArray()
        val totalBytes = files.sumOf { it.length() }
        val count = files.size

        val formattedSize = if (totalBytes < 1024 * 1024) {
            val kb = totalBytes / 1024.0
            String.format(Locale.getDefault(), "%.1f KB", kb)
        } else {
            val mb = totalBytes / (1024.0 * 1024.0)
            String.format(Locale.getDefault(), "%.1f MB", mb)
        }

        return StorageInfo(
            cachedBytes = totalBytes,
            formattedSize = formattedSize,
            cachedFilesCount = count
        )
    }
}
