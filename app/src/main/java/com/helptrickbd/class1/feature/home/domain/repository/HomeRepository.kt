package com.helptrickbd.class1.feature.home.domain.repository

import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Home and Book catalog operations.
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
}
