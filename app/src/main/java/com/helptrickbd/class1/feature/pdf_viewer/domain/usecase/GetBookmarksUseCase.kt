package com.helptrickbd.class1.feature.pdf_viewer.domain.usecase

import com.helptrickbd.class1.feature.pdf_viewer.domain.model.Bookmark
import com.helptrickbd.class1.feature.pdf_viewer.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBookmarksUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    operator fun invoke(bookId: String?): Flow<List<Bookmark>> {
        if (bookId.isNullOrBlank()) return flowOf(emptyList())
        return bookmarkRepository.getBookmarks(bookId)
    }
}
