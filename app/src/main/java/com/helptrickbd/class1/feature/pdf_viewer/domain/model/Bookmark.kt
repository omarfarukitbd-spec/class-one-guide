package com.helptrickbd.class1.feature.pdf_viewer.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Bookmark(
    val id: Long = 0L,
    val bookId: String,
    val pageNumber: Int,
    val title: String = "",
    val note: String? = null,
    val createdTimestamp: Long = System.currentTimeMillis()
)
