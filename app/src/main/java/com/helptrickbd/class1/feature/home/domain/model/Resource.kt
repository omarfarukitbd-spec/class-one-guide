package com.helptrickbd.class1.feature.home.domain.model

import androidx.compose.runtime.Immutable

/**
 * Domain model for a specific study resource (e.g., a PDF file).
 */
@Immutable
data class Resource(
    val resourceId: String,
    val title: String,
    val pdfUrl: String,
    val type: ResourceType,
    val iconName: String? = null
)
