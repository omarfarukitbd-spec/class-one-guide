package com.helptrickbd.class1.feature.subject_detail.domain.repository

import com.helptrickbd.class1.feature.home.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {
    fun getBooksForSubject(subjectId: String): Flow<List<Book>>
}
