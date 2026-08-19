package com.helptrickbd.class1.feature.subject_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.feature.subject_detail.domain.usecase.GetBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {

    private val subjectDetailRoute: Screen.SubjectDetail = savedStateHandle.toRoute()
    val subjectId = subjectDetailRoute.subjectId
    val subjectName = subjectDetailRoute.subjectName

    private val _uiState = MutableStateFlow<SubjectDetailUiState>(SubjectDetailUiState.Loading)
    val uiState: StateFlow<SubjectDetailUiState> = _uiState.asStateFlow()

    init {
        loadSubjectDetails()
    }

    fun loadSubjectDetails() {
        viewModelScope.launch {
            _uiState.value = SubjectDetailUiState.Loading
            val result = getBooksUseCase(subjectId)
            if (result.isSuccess) {
                // Conditional feature flags based on subject
                val featureFlags = mapOf(
                    "drawing_board" to true,
                    "calculator" to (subjectName.contains("Math", ignoreCase = true) || subjectName.contains("Physics", ignoreCase = true)),
                    "quiz" to true
                )
                _uiState.value = SubjectDetailUiState.Success(
                    subjectName = subjectName,
                    books = result.getOrThrow(),
                    features = featureFlags
                )
            } else {
                _uiState.value = SubjectDetailUiState.Error(
                    result.exceptionOrNull()?.message ?: "Failed to load books"
                )
            }
        }
    }
}
