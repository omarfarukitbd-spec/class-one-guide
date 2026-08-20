package com.helptrickbd.class1.feature.home.domain.model

/**
 * Domain model representing a single book or guide.
 */
data class Book(
    val bookId: String,
    val title: String,
    val subtitle: String? = null,
    val pdfUrl: String = "",
    val coverUrl: String? = null,
    val curriculum: Curriculum = Curriculum.SCHOOL,
    val availableVersions: List<LanguageVersion> = listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH),
    val chapters: List<Chapter> = emptyList(),
    val isFavorite: Boolean = false,
    val progressPercent: Float = 0f
)
