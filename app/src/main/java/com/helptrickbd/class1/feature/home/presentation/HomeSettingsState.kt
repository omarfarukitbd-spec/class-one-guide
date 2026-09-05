package com.helptrickbd.class1.feature.home.presentation

import androidx.compose.runtime.Immutable
import com.helptrickbd.class1.core.settings.domain.model.StorageInfo
import com.helptrickbd.class1.core.settings.domain.model.ThemeMode
import com.helptrickbd.class1.feature.home.domain.model.LayoutMode

@Immutable
data class HomeSettingsState(
    val storageInfo: StorageInfo = StorageInfo(),
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val layoutMode: LayoutMode = LayoutMode.GRID,
    val unreadNotifications: Int = 0
)
