package com.helptrickbd.class1.feature.pdf_viewer.domain.usecase

import com.helptrickbd.class1.feature.pdf_viewer.domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToggleBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(bookId: String?, pageNumber: Int, title: String): Boolean = withContext(Dispatchers.IO) {
        if (bookId.isNullOrBlank() || pageNumber < 1) return@withContext false

        val isBookmarked = bookmarkRepository.isPageBookmarked(bookId, pageNumber)
        if (isBookmarked) {
            bookmarkRepository.deleteBookmarkByPage(bookId, pageNumber)
            false
        } else {
            bookmarkRepository.addBookmark(
                bookId = bookId,
                pageNumber = pageNumber,
                title = title
            )
            true
        }
    }
}
