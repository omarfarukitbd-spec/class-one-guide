package com.helptrickbd.class1.feature.home.domain.model

/**
 * Represents a subject within a class.
 * @param subjectId Unique identifier for the subject.
 * @param subjectName Name of the subject (e.g., Mathematics, Physics).
 * @param iconUrl URL for the subject's icon stored in storage.
 */
data class Subject(
    val subjectId: String = "",
    val subjectName: String = "",
    val iconUrl: String = ""
)
