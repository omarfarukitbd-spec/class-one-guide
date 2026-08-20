package com.helptrickbd.class1.feature.subject_detail.domain.repository

import com.helptrickbd.class1.feature.home.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {
    fun getBookDetail(bookId: String): Flow<Book?>
    suspend fun toggleFavorite(bookId: String, isFavorite: Boolean)
}
