package com.helptrickbd.class1.core.settings.domain.repository

import com.helptrickbd.class1.core.settings.domain.model.StorageInfo
import com.helptrickbd.class1.core.settings.domain.model.ThemeMode
import com.helptrickbd.class1.feature.home.domain.model.LayoutMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getStorageInfo(): Flow<StorageInfo>
    suspend fun clearPdfCache(): Boolean
    fun getThemeMode(): Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
    fun getLayoutMode(): Flow<LayoutMode>
    suspend fun setLayoutMode(mode: LayoutMode)
}
