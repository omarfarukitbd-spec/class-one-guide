package com.helptrickbd.class1.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books WHERE curriculum = :curriculum")
    fun getBooksByCurriculum(curriculum: Curriculum): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE bookId = :bookId")
    fun getBookById(bookId: String): Flow<BookEntity?>

    @Query("SELECT * FROM books WHERE lastReadTimestamp > 0 ORDER BY lastReadTimestamp DESC LIMIT 1")
    fun getLatestReadBook(): Flow<BookEntity?>

    @Query("SELECT * FROM books WHERE isFavorite = 1 ORDER BY title ASC")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books ORDER BY title ASC")
    fun getAllBooksFlow(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE curriculum = :curriculum AND (title LIKE '%' || :query || '%' OR subtitle LIKE '%' || :query || '%')")
    fun searchBooks(curriculum: Curriculum, query: String): Flow<List<BookEntity>>

    @Query("SELECT * FROM books")
    suspend fun getAllBooksDirect(): List<BookEntity>

    @Query("SELECT COUNT(*) FROM books")
    suspend fun getBookCount(): Int

    @Query("UPDATE books SET lastReadPage = :page, progressPercent = :progress, lastReadTimestamp = :timestamp WHERE bookId = :bookId")
    suspend fun updateReadingProgress(bookId: String, page: Int, progress: Float, timestamp: Long)

    @Query("UPDATE books SET isFavorite = :isFavorite WHERE bookId = :bookId")
    suspend fun toggleFavorite(bookId: String, isFavorite: Boolean)

    @Query("DELETE FROM books WHERE bookId NOT IN (:activeIds)")
    suspend fun deleteBooksNotIn(activeIds: List<String>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)
}

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters WHERE bookId = :bookId ORDER BY orderIndex ASC")
    fun getChaptersForBook(bookId: String): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters WHERE bookId = :bookId ORDER BY orderIndex ASC")
    suspend fun getChaptersForBookDirect(bookId: String): List<ChapterEntity>

    @Query("SELECT * FROM chapters")
    fun getAllChaptersFlow(): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters WHERE title LIKE '%' || :query || '%' OR unitNo LIKE '%' || :query || '%'")
    fun searchChapters(query: String): Flow<List<ChapterEntity>>

    // Efficiently find chapters that belong to a specific curriculum through a JOIN
    @Query("""
        SELECT chapters.* FROM chapters 
        INNER JOIN books ON chapters.bookId = books.bookId 
        WHERE books.curriculum = :curriculum 
        AND (chapters.title LIKE '%' || :query || '%' OR chapters.unitNo LIKE '%' || :query || '%')
    """)
    fun searchChaptersInCurriculum(curriculum: Curriculum, query: String): Flow<List<ChapterEntity>>

    @Query("SELECT COUNT(*) FROM chapters")
    suspend fun getChapterCount(): Int

    @Query("DELETE FROM chapters WHERE bookId = :bookId")
    suspend fun deleteChaptersForBook(bookId: String)

    @Query("DELETE FROM chapters WHERE bookId NOT IN (:activeIds)")
    suspend fun deleteChaptersForBooksNotIn(activeIds: List<String>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>)
}

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks WHERE bookId = :bookId ORDER BY pageNumber ASC")
    fun getBookmarksForBook(bookId: String): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE bookId = :bookId AND pageNumber = :pageNumber)")
    fun isPageBookmarkedFlow(bookId: String, pageNumber: Int): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE bookId = :bookId AND pageNumber = :pageNumber)")
    suspend fun isPageBookmarked(bookId: String, pageNumber: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity): Long

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun deleteBookmark(id: Long)

    @Query("DELETE FROM bookmarks WHERE bookId = :bookId AND pageNumber = :pageNumber")
    suspend fun deleteBookmarkByPage(bookId: String, pageNumber: Int)

    @Query("DELETE FROM bookmarks WHERE bookId = :bookId")
    suspend fun deleteAllBookmarksForBook(bookId: String)
}

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM notifications WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: String)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllAsRead()

    @Query("DELETE FROM notifications WHERE id = :id")
    suspend fun deleteNotification(id: String)

    @Query("DELETE FROM notifications")
    suspend fun clearAllNotifications()
}
