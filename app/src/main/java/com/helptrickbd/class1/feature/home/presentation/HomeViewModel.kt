package com.helptrickbd.class1.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _selectedCurriculum = MutableStateFlow(AppConfig.DEFAULT_CURRICULUM)
    val selectedCurriculum = _selectedCurriculum.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> = _selectedCurriculum
        .flatMapLatest { curriculum ->
            combine(
                repository.getSubjects(curriculum),
                repository.getResumeBook()
            ) { subjects, resumeBook ->
                HomeUiState.Success(
                    userName = "ওমর ফারুক",
                    selectedCurriculum = curriculum,
                    resumeBook = resumeBook,
                    subjects = subjects
                ) as HomeUiState
            }
        }
        .catch { e -> emit(HomeUiState.Error(e.message ?: "Unknown Error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading
        )

    fun onCurriculumSelected(curriculum: Curriculum) {
        _selectedCurriculum.value = curriculum
    }
}
