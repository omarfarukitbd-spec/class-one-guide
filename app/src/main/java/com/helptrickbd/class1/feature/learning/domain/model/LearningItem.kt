package com.helptrickbd.class1.feature.learning.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class LearningItem(
    val id: String,
    val character: String,
    val wordExample: String? = null,
    val wordMeaning: String? = null,
    val category: LearningCategory,
    val audioPath: String? = null,
    val imageRes: Int? = null
)
