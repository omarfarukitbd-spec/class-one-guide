package com.helptrickbd.class1.feature.subject_detail.data.repository

import com.helptrickbd.class1.feature.home.data.datasource.MadrasahBooksData
import com.helptrickbd.class1.feature.home.data.datasource.SchoolBooksData
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.subject_detail.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectRepositoryImpl @Inject constructor() : SubjectRepository {
    override fun getBookDetail(bookId: String): Flow<Book?> {
        val allBooks = SchoolBooksData.books + MadrasahBooksData.books
        val book = allBooks.find { it.bookId == bookId } ?: allBooks.firstOrNull()
        return flowOf(book)
    }
}
