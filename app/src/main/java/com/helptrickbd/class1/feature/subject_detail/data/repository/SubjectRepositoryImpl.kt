package com.helptrickbd.class1.feature.subject_detail.data.repository

import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.subject_detail.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectRepositoryImpl @Inject constructor() : SubjectRepository {
    override fun getBooksForSubject(subjectId: String): Flow<List<Book>> = flowOf(
        listOf(
            Book("b1", "বই ১", "", progressPercent = 0.8f),
            Book("b2", "বই ২", "", progressPercent = 0.2f)
        )
    )
}
