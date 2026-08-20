package com.helptrickbd.class1.feature.home.data.repository

import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Subject
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mock implementation of [HomeRepository] for development.
 */
@Singleton
class HomeRepositoryImpl @Inject constructor() : HomeRepository {

    override fun getSubjects(): Flow<List<Subject>> = flowOf(
        listOf(
            Subject("1", "বাংলা ১ম পত্র", listOf(Book("b1", "বই ১", ""))),
            Subject("2", "English For Today", listOf(Book("b2", "Book 2", ""))),
            Subject("3", "গণিত", listOf(Book("b3", "গণিত বই", ""))),
            Subject("4", "বিজ্ঞান", listOf(Book("b4", "বিজ্ঞান বই", "")))
        )
    )

    override fun getResumeBook(): Flow<Book?> = flowOf(
        Book(
            bookId = "resume_1",
            title = "বাংলা ১ম পত্র",
            pdfUrl = "",
            progressPercent = 0.65f
        )
    )
}
