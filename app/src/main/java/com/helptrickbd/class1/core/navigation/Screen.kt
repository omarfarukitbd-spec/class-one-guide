package com.helptrickbd.class1.core.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe Navigation Routes for Jetpack Navigation Compose.
 */
sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data object Favorites : Screen

    @Serializable
    data object Settings : Screen

    // --- Learning / Hatekhari Section ---
    @Serializable
    data object LearningHub : Screen

    @Serializable
    data object AlphabetSlate : Screen

    @Serializable
    data object QuizGames : Screen

    @Serializable
    data class SubjectDetail(
        val subjectId: String,
        val subjectName: String = ""
    ) : Screen

    @Serializable
    data class PdfViewer(
        val resourceTitle: String,
        val pdfUrl: String,
        val bookId: String = "",
        val initialPage: Int = 1
    ) : Screen
}
