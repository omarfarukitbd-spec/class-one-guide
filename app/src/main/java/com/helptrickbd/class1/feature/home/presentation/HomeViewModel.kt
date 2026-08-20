package com.helptrickbd.class1.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            combine(
                repository.getSubjects(),
                repository.getResumeBook()
            ) { subjects, resumeBook ->
                HomeUiState.Success(
                    userName = "ওমর ফারুক",
                    resumeBook = resumeBook,
                    subjects = subjects
                )
            }
            .catch { e ->
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown Error")
            }
            .collect { state ->
                _uiState.value = state
            }
        }
    }
}
