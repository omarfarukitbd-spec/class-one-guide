package com.helptrickbd.class1.feature.home.data.repository

import com.helptrickbd.class1.feature.home.data.datasource.MadrasahBooksData
import com.helptrickbd.class1.feature.home.data.datasource.SchoolBooksData
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor() : HomeRepository {

    override fun getBooks(curriculum: Curriculum): Flow<List<Book>> {
        val books = if (curriculum == Curriculum.SCHOOL) {
            SchoolBooksData.books
        } else {
            MadrasahBooksData.books
        }
        return flowOf(books)
    }

    override fun getBookById(bookId: String): Flow<Book?> {
        val allBooks = SchoolBooksData.books + MadrasahBooksData.books
        val book = allBooks.find { it.bookId == bookId }
        return flowOf(book)
    }

    override fun getResumeBook(): Flow<Book?> {
        val resume = SchoolBooksData.books.firstOrNull()
        return flowOf(resume)
    }
}
