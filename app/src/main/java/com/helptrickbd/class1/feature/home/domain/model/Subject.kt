package com.helptrickbd.class1.feature.home.domain.model

import androidx.compose.runtime.Immutable

/**
 * Domain model representing a study subject containing multiple books.
 */
@Immutable
data class Subject(
    val subjectId: String,
    val subjectName: String,
    val books: List<Book> = emptyList(),
    val iconUrl: String? = null
)
