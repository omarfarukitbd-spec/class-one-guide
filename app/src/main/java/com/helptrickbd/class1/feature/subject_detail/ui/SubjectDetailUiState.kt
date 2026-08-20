package com.helptrickbd.class1.feature.subject_detail.ui

import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Chapter
import com.helptrickbd.class1.feature.home.domain.model.LanguageVersion

sealed interface SubjectDetailUiState {
    data object Loading : SubjectDetailUiState

    data class Success(
        val book: Book,
        val selectedVersion: LanguageVersion,
        val chapters: List<Chapter>,
        val expandedChapterId: String? = null
    ) : SubjectDetailUiState

    data class Error(val message: String) : SubjectDetailUiState
}
