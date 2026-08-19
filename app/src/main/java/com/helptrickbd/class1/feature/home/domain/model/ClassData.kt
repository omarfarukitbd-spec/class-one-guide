package com.helptrickbd.class1.feature.home.domain.model

/**
 * Represents a specific class (e.g., Class 10, Class 12) in the educational app.
 * @param classId Unique identifier for the class.
 * @param className Name of the class.
 * @param features Map of feature flags to enable/disable specific modules for this class.
 */
data class ClassData(
    val classId: String = "",
    val className: String = "",
    val features: Map<String, Boolean> = emptyMap()
)
