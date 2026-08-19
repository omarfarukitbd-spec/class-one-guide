package com.helptrickbd.class1.feature.subject_detail.domain.usecase

import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.subject_detail.domain.repository.SubjectRepository
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: SubjectRepository
) {
    suspend operator fun invoke(subjectId: String): Result<List<Book>> {
        return repository.getBooks(subjectId)
    }
}
