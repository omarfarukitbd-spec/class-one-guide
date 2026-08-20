package com.helptrickbd.class1.feature.home.domain.model

/**
 * Represents the educational curriculum stream.
 */
enum class Curriculum(val titleBangla: String) {
    SCHOOL("স্কুল"),
    MADRASAH("মাদ্রাসা")
}

/**
 * Represents the language medium / version of the content.
 */
enum class LanguageVersion(val titleBangla: String) {
    BANGLA("বাংলা ভার্সন"),
    ENGLISH("ইংরেজি ভার্সন")
}

/**
 * Represents the study resource category.
 */
enum class ResourceType(val defaultTitle: String) {
    TEXTBOOK("মূল বই পড়ুন"),
    GUIDEBOOK("গাইডবুক পড়ুন"),
    MODEL_TEST("মডেল টেস্ট")
}
