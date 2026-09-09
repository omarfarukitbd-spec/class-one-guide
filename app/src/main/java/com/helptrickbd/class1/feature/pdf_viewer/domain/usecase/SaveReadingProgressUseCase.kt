package com.helptrickbd.class1.feature.pdf_viewer.domain.usecase

import com.helptrickbd.class1.core.database.BookDao
import com.helptrickbd.class1.core.database.ChapterDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveReadingProgressUseCase @Inject constructor(
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao
) {
    suspend operator fun invoke(
        bookId: String?,
        page: Int,
        totalPages: Int,
        chapterId: String? = null
    ) = withContext(Dispatchers.IO) {
        if (bookId.isNullOrBlank() || page < 1) return@withContext

        // If a specific chapter is being read, check if user finished reading it (reached last page)
        if (!chapterId.isNullOrBlank() && totalPages > 0 && page >= totalPages) {
            chapterDao.updateChapterCompletion(chapterId, true)
            val allChapters = chapterDao.getChaptersForBookDirect(bookId)
            if (allChapters.isNotEmpty()) {
                val completedCount = allChapters.count { 
                    if (it.chapterId == chapterId) true else it.isCompleted 
                }
                val newProgress = (completedCount.toFloat() / allChapters.size.toFloat()).coerceIn(0f, 1f)
                bookDao.updateReadingProgress(
                    bookId = bookId,
                    page = page,
                    progress = newProgress,
                    timestamp = System.currentTimeMillis()
                )
                return@withContext
            }
        }

        val progress = if (totalPages > 0) {
            (page.toFloat() / totalPages.toFloat()).coerceIn(0f, 1f)
        } else {
            0f
        }

        bookDao.updateReadingProgress(
            bookId = bookId,
            page = page,
            progress = progress,
            timestamp = System.currentTimeMillis()
        )
    }
}
