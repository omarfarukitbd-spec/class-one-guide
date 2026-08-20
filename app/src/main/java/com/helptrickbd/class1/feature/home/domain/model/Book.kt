package com.helptrickbd.class1.feature.home.domain.model

/**
 * Domain model representing a single book or guide.
 */
data class Book(
    val bookId: String,
    val title: String,
    val pdfUrl: String,
    val coverUrl: String? = null,
    val isFavorite: Boolean = false,
    val progressPercent: Float = 0f
)
