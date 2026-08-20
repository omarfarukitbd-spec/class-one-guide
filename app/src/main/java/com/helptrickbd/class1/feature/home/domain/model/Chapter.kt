package com.helptrickbd.class1.feature.home.domain.model

/**
 * Domain model representing a chapter or unit in a book.
 */
data class Chapter(
    val chapterId: String,
    val unitNo: String, // e.g., "ইউনিট ১"
    val title: String,
    val version: LanguageVersion = LanguageVersion.BANGLA,
    val resources: List<Resource> = emptyList()
)
