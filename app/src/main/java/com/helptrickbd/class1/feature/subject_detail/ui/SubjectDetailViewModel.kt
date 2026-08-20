package com.helptrickbd.class1.feature.subject_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.subject_detail.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SubjectRepository
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
            repository.getBooksForSubject(subjectId)
                .catch { e ->
                    _uiState.value = SubjectDetailUiState.Error(e.message ?: "Failed to load books")
                }
                .collect { books ->
                    val featureFlags = mapOf(
                        "drawing_board" to true,
                        "quiz" to true
                    )
                    _uiState.value = SubjectDetailUiState.Success(
                        subjectName = subjectName,
                        books = books.map { 
                            // Map domain book to whatever UI expects or just use it
                            Book(
                                it.bookId, it.title, it.pdfUrl, it.coverUrl, it.isFavorite, it.progressPercent
                            )
                        },
                        features = featureFlags
                    )
                }
        }
    }
}
