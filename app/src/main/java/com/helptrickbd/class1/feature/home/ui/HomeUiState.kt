package com.helptrickbd.class1.feature.home.ui

import com.helptrickbd.class1.feature.home.domain.model.ClassData
import com.helptrickbd.class1.feature.home.domain.model.Subject

sealed interface HomeUiState {
    data object Loading : HomeUiState
    
    data class Success(
        val classData: ClassData,
        val subjects: List<Subject>
    ) : HomeUiState
    
    data class Error(val message: String) : HomeUiState
}
