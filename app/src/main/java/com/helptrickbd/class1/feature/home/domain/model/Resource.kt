package com.helptrickbd.class1.feature.home.domain.model

/**
 * Domain model for a specific study resource (e.g., a PDF file).
 */
data class Resource(
    val resourceId: String,
    val title: String,
    val pdfUrl: String,
    val type: ResourceType,
    val iconName: String? = null
)
