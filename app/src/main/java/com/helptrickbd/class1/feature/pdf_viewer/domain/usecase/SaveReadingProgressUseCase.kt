package com.helptrickbd.class1.feature.pdf_viewer.domain.usecase

import com.helptrickbd.class1.core.database.BookDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveReadingProgressUseCase @Inject constructor(
    private val bookDao: BookDao
) {
    suspend operator fun invoke(bookId: String?, page: Int, totalPages: Int) = withContext(Dispatchers.IO) {
        if (bookId.isNullOrBlank() || page < 1) return@withContext

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
