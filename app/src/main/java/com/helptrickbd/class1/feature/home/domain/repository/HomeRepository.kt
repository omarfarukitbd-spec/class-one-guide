package com.helptrickbd.class1.feature.home.domain.repository

import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.SearchResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Home, Book catalog, Search, and Favorites operations.
 */
interface HomeRepository {
    /**
     * Retrieves all books for the specified curriculum stream (School / Madrasah).
     */
    fun getBooks(curriculum: Curriculum): Flow<List<Book>>

    /**
     * Retrieves details for a specific book by ID.
     */
    fun getBookById(bookId: String): Flow<Book?>

    /**
     * Retrieves current reading progress.
     */
    fun getResumeBook(): Flow<Book?>

    /**
     * Retrieves all books marked as favorite.
     */
    fun getFavoriteBooks(): Flow<List<Book>>

    /**
     * Toggles favorite status for a book.
     */
    suspend fun toggleFavorite(bookId: String, isFavorite: Boolean)

    /**
     * Globally searches across book titles, unit numbers, and lesson titles.
     */
    fun searchBooksAndChapters(query: String, curriculum: Curriculum): Flow<List<SearchResult>>

    /**
     * Retrieves remote in-app notice broadcast from Firestore Admin Panel.
     */
    fun getCloudNotice(): Flow<String?>
}
