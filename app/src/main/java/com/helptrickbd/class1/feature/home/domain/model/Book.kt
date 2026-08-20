package com.helptrickbd.class1.feature.home.domain.model

/**
 * Domain model representing a single book or guide.
 */
data class Book(
    val bookId: String,
    val title: String,
    val pdfUrl: String, // Fallback for simple cases, or root PDF
    val coverUrl: String? = null,
    val curriculum: Curriculum = Curriculum.SCHOOL,
    val version: LanguageVersion = LanguageVersion.BANGLA,
    val chapters: List<Chapter> = emptyList(),
    val isFavorite: Boolean = false,
    val progressPercent: Float = 0f
)
