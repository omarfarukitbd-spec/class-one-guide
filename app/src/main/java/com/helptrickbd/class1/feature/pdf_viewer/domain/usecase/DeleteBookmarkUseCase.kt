package com.helptrickbd.class1.feature.pdf_viewer.domain.usecase

import com.helptrickbd.class1.feature.pdf_viewer.domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(bookmarkId: Long) = withContext(Dispatchers.IO) {
        if (bookmarkId <= 0) return@withContext
        bookmarkRepository.deleteBookmark(bookmarkId)
    }
}
