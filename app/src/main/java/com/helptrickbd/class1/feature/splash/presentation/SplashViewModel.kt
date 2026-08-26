package com.helptrickbd.class1.feature.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.core.database.DatabaseSeeder
import com.helptrickbd.class1.core.di.IoDispatcher
import com.helptrickbd.class1.core.sync.domain.usecase.SyncCloudDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val seeder: DatabaseSeeder,
    private val syncCloudDataUseCase: SyncCloudDataUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                seeder.seedIfNeeded()
            }
            // Launch cloud sync in background non-blocking
            launch(ioDispatcher) {
                syncCloudDataUseCase()
            }
            delay(1000)
            _isReady.value = true
        }
    }
}
