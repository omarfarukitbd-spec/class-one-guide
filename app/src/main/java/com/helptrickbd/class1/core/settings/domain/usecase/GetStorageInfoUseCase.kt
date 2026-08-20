package com.helptrickbd.class1.core.settings.domain.usecase

import com.helptrickbd.class1.core.settings.domain.model.StorageInfo
import com.helptrickbd.class1.core.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetStorageInfoUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<StorageInfo> {
        return repository.getStorageInfo()
    }
}
