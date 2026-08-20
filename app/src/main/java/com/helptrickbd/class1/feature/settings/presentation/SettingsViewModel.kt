package com.helptrickbd.class1.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.core.settings.domain.model.StorageInfo
import com.helptrickbd.class1.core.settings.domain.model.ThemeMode
import com.helptrickbd.class1.core.settings.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val storageInfo: StorageInfo = StorageInfo(),
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        repository.getStorageInfo(),
        repository.getThemeMode()
    ) { storage, theme ->
        SettingsUiState(storageInfo = storage, themeMode = theme)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingsUiState()
    )

    fun onThemeSelected(mode: ThemeMode) {
        viewModelScope.launch {
            repository.setThemeMode(mode)
        }
    }

    fun onClearCache() {
        viewModelScope.launch {
            repository.clearPdfCache()
        }
    }
}
