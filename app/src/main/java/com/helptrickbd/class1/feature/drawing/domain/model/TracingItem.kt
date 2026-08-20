package com.helptrickbd.class1.feature.drawing.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class TracingItem(
    val id: String,
    val character: String,
    val wordExample: String,
    val meaning: String = "",
    val category: TracingCategory,
    val orderIndex: Int
)
