package com.helptrickbd.class1.feature.pdf_viewer.domain.repository

import com.helptrickbd.class1.feature.pdf_viewer.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarks(bookId: String): Flow<List<Bookmark>>
    fun isPageBookmarkedFlow(bookId: String, pageNumber: Int): Flow<Boolean>
    suspend fun isPageBookmarked(bookId: String, pageNumber: Int): Boolean
    suspend fun addBookmark(bookId: String, pageNumber: Int, title: String, note: String? = null): Long
    suspend fun deleteBookmark(id: Long)
    suspend fun deleteBookmarkByPage(bookId: String, pageNumber: Int)
}
