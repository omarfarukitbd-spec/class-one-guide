package com.helptrickbd.class1.feature.home.domain.model

import androidx.compose.runtime.Immutable

/**
 * Domain model representing a single book or guide.
 */
@Immutable
data class Book(
    val bookId: String,
    val title: String,
    val subtitle: String? = null,
    val pdfUrl: String = "",
    val coverUrl: String? = null,
    val curriculum: Curriculum = Curriculum.SCHOOL,
    val availableVersions: List<LanguageVersion> = listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH),
    val chapters: List<Chapter> = emptyList(),
    val totalChapters: Int = 0,
    val isFavorite: Boolean = false,
    val progressPercent: Float = 0f,
    val lastReadPage: Int = 1
)
