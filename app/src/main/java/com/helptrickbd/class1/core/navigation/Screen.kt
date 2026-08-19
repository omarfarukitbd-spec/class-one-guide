package com.helptrickbd.class1.core.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe Navigation Routes for Jetpack Navigation Compose.
 */
sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data class SubjectDetail(
        val subjectId: String,
        val subjectName: String
    ) : Screen

    @Serializable
    data class PdfViewer(
        val bookId: String,
        val pdfUrl: String
    ) : Screen
}
