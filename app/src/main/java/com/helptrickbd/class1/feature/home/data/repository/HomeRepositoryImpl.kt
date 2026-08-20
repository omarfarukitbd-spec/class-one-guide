package com.helptrickbd.class1.feature.home.data.repository

import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
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

    private val schoolSubjects = listOf(
        Subject("s1", "বাংলা", listOf(Book("b1", "আমার বাংলা বই", ""))),
        Subject("s2", "English", listOf(Book("b2", "English for Today", ""))),
        Subject("s3", "গণিত", listOf(Book("b3", "প্রাথমিক গণিত", "")))
    )

    private val madrasahSubjects = listOf(
        Subject("m1", "কুরআন মাজীদ", listOf(Book("b4", "কুরআন মাজীদ ও তাজবীদ", ""))),
        Subject("m2", "আকাইদ ও ফিকহ", listOf(Book("b5", "আকাইদ ও ফিকহ", ""))),
        Subject("m3", "বাংলা", listOf(Book("b1", "আমার বাংলা বই", "")))
    )

    override fun getSubjects(curriculum: Curriculum): Flow<List<Subject>> = flowOf(
        if (curriculum == Curriculum.SCHOOL) schoolSubjects else madrasahSubjects
    )

    override fun getResumeBook(): Flow<Book?> = flowOf(
        Book(
            bookId = "resume_1",
            title = "আমার বাংলা বই",
            pdfUrl = "",
            progressPercent = 0.42f
        )
    )
}
