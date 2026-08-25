package com.helptrickbd.class1.core.database

import com.helptrickbd.class1.feature.home.data.datasource.MadrasahBooksData
import com.helptrickbd.class1.feature.home.data.datasource.SchoolBooksData
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
        val allDomainBooks = SchoolBooksData.books + MadrasahBooksData.books
        val existingBooks = bookDao.getAllBooksDirect().associateBy { it.bookId }

        val bookEntities = allDomainBooks.map { book ->
            val existing = existingBooks[book.bookId]
            BookEntity(
                bookId = book.bookId,
                title = book.title,
                subtitle = book.subtitle,
                pdfUrl = book.pdfUrl,
                coverUrl = book.coverUrl,
                curriculum = book.curriculum,
                availableVersions = book.availableVersions,
                totalChapters = book.chapters.size,
                isFavorite = existing?.isFavorite ?: book.isFavorite,
                progressPercent = existing?.progressPercent ?: book.progressPercent,
                lastReadPage = existing?.lastReadPage ?: 1,
                lastReadTimestamp = existing?.lastReadTimestamp ?: 0L
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
