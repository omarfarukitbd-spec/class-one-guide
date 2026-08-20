package com.helptrickbd.class1.feature.pdf_viewer.data.repository

import com.helptrickbd.class1.core.database.BookmarkDao
import com.helptrickbd.class1.core.database.BookmarkEntity
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.Bookmark
import com.helptrickbd.class1.feature.pdf_viewer.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {

    override fun getBookmarks(bookId: String): Flow<List<Bookmark>> {
        return bookmarkDao.getBookmarksForBook(bookId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun isPageBookmarkedFlow(bookId: String, pageNumber: Int): Flow<Boolean> {
        return bookmarkDao.isPageBookmarkedFlow(bookId, pageNumber)
    }

    override suspend fun isPageBookmarked(bookId: String, pageNumber: Int): Boolean {
        return bookmarkDao.isPageBookmarked(bookId, pageNumber)
    }

    override suspend fun addBookmark(bookId: String, pageNumber: Int, title: String, note: String?): Long {
        val entity = BookmarkEntity(
            bookId = bookId,
            pageNumber = pageNumber,
            title = title,
            note = note,
            createdTimestamp = System.currentTimeMillis()
        )
        return bookmarkDao.insertBookmark(entity)
    }

    override suspend fun deleteBookmark(id: Long) {
        bookmarkDao.deleteBookmark(id)
    }

    override suspend fun deleteBookmarkByPage(bookId: String, pageNumber: Int) {
        bookmarkDao.deleteBookmarkByPage(bookId, pageNumber)
    }

    private fun BookmarkEntity.toDomain() = Bookmark(
        id = id,
        bookId = bookId,
        pageNumber = pageNumber,
        title = title,
        note = note,
        createdTimestamp = createdTimestamp
    )
}
