package com.helptrickbd.class1.core.database

import androidx.room.withTransaction
import com.helptrickbd.class1.feature.home.data.datasource.MadrasahBooksData
import com.helptrickbd.class1.feature.home.data.datasource.SchoolBooksData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Local Database Seeder to ensure the app has baseline data even without internet on first launch.
 */
@Singleton
class DatabaseSeeder @Inject constructor(
    private val database: AppDatabase,
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao
) {
    @Volatile
    private var isSeeded = false

    /**
     * Seeds the local DB with offline data if not already present.
     * Logic: Preserves user favorites and progress even during manual force seeding.
     */
    suspend fun seedIfNeeded(force: Boolean = false) = withContext(Dispatchers.IO) {
        if (isSeeded && !force) return@withContext
        
        val allDomainBooks = SchoolBooksData.books + MadrasahBooksData.books
        val existingBooks = bookDao.getAllBooksDirect().associateBy { it.bookId }
        
        // If local DB is already populated, we skip seeding unless forced
        if (existingBooks.size >= allDomainBooks.size && !force) {
            isSeeded = true
            return@withContext
        }

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
                val uniqueId = if (chapter.chapterId.startsWith(book.bookId)) {
                    chapter.chapterId
                } else {
                    "${book.bookId}_${chapter.chapterId}"
                }
                ChapterEntity(
                    chapterId = uniqueId,
                    bookId = book.bookId,
                    unitNo = chapter.unitNo,
                    title = chapter.title,
                    version = chapter.version,
                    resources = chapter.resources,
                    orderIndex = index
                )
            }
        }

        // Logic Fix: Use transaction to ensure data integrity
        database.withTransaction {
            bookDao.insertBooks(bookEntities)
            chapterDao.insertChapters(chapterEntities)
        }
        
        isSeeded = true
    }
}
