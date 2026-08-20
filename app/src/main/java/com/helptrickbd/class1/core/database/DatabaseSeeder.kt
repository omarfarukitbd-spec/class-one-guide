package com.helptrickbd.class1.core.database

import com.helptrickbd.class1.feature.home.data.datasource.MadrasahBooksData
import com.helptrickbd.class1.feature.home.data.datasource.SchoolBooksData
import com.helptrickbd.class1.feature.home.domain.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseSeeder @Inject constructor(
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao
) {
    suspend fun seedIfNeeded() = withContext(Dispatchers.IO) {
        val existingCount = bookDao.getBookCount()
        if (existingCount > 0) return@withContext

        val allDomainBooks = SchoolBooksData.books + MadrasahBooksData.books

        val bookEntities = allDomainBooks.map { book ->
            BookEntity(
                bookId = book.bookId,
                title = book.title,
                subtitle = book.subtitle,
                pdfUrl = book.pdfUrl,
                coverUrl = book.coverUrl,
                curriculum = book.curriculum,
                availableVersions = book.availableVersions,
                totalChapters = book.chapters.size,
                isFavorite = book.isFavorite,
                progressPercent = book.progressPercent,
                lastReadPage = 1,
                lastReadTimestamp = 0L
            )
        }

        val chapterEntities = allDomainBooks.flatMap { book ->
            book.chapters.mapIndexed { index, chapter ->
                ChapterEntity(
                    chapterId = chapter.chapterId,
                    bookId = book.bookId,
                    unitNo = chapter.unitNo,
                    title = chapter.title,
                    version = chapter.version,
                    resources = chapter.resources,
                    orderIndex = index
                )
            }
        }

        bookDao.insertBooks(bookEntities)
        chapterDao.insertChapters(chapterEntities)
    }
}
