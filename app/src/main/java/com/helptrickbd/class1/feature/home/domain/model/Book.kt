package com.helptrickbd.class1.feature.home.domain.model

/**
 * Represents a book or guide under a specific subject.
 * @param bookId Unique identifier for the book.
 * @param bookName Title of the book.
 * @param bookType Type of the book (e.g., "Textbook", "Guide").
 * @param pdfUrl Firebase Storage URL for the PDF file.
 */
data class Book(
    val bookId: String = "",
    val bookName: String = "",
    val bookType: String = "",
    val pdfUrl: String = ""
)
