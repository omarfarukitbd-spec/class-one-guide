package com.helptrickbd.class1.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.feature.home.domain.usecase.GetClassDataUseCase
import com.helptrickbd.class1.feature.home.domain.usecase.GetSubjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getClassDataUseCase: GetClassDataUseCase,
    private val getSubjectsUseCase: GetSubjectsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchDashboardData()
    }

    private fun fetchDashboardData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            
            val classDataDeferred = async { getClassDataUseCase("class_1") }
            val subjectsDeferred = async { getSubjectsUseCase("class_1") }
            
            val classResult = classDataDeferred.await()
            val subjectsResult = subjectsDeferred.await()
            
            if (classResult.isSuccess && subjectsResult.isSuccess) {
                _uiState.value = HomeUiState.Success(
                    classData = classResult.getOrThrow(),
                    subjects = subjectsResult.getOrThrow()
                )
            } else {
                val error = classResult.exceptionOrNull()?.message 
                    ?: subjectsResult.exceptionOrNull()?.message 
                    ?: "Unknown Error"
                _uiState.value = HomeUiState.Error(error)
            }
        }
    }
}
