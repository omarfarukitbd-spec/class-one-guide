package com.helptrickbd.class1.feature.home.domain.model

import androidx.compose.runtime.Immutable

/**
 * Domain model representing a global search match across books, units, or lessons.
 */
@Immutable
data class SearchResult(
    val book: Book,
    val matchedUnitNo: String? = null,
    val matchedChapterTitle: String? = null
)
