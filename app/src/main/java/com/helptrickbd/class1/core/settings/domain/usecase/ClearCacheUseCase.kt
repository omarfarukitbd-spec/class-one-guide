package com.helptrickbd.class1.core.settings.domain.usecase

import com.helptrickbd.class1.core.settings.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearCacheUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.clearPdfCache()
    }
}
