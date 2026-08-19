package com.helptrickbd.class1.feature.subject_detail.ui

import com.helptrickbd.class1.feature.home.domain.model.Book

sealed interface SubjectDetailUiState {
    data object Loading : SubjectDetailUiState

    data class Success(
        val subjectName: String,
        val books: List<Book>,
        val features: Map<String, Boolean> = emptyMap()
    ) : SubjectDetailUiState

    data class Error(val message: String) : SubjectDetailUiState
}
