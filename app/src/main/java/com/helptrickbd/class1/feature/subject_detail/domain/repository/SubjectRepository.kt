package com.helptrickbd.class1.feature.subject_detail.domain.repository

import com.helptrickbd.class1.feature.home.domain.model.Book

interface SubjectRepository {
    suspend fun getBooks(subjectId: String): Result<List<Book>>
}
