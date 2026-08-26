package com.helptrickbd.class1.feature.home.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

data class SubjectThemeConfig(
    val primaryIcon: ImageVector,
    val categoryBadge: String
)

object SubjectThemeResolver {
    fun resolve(title: String): SubjectThemeConfig {
        val lower = title.lowercase()
        return when {
            // ১. কুরআন মাজীদ ও তাজভীদ (ইবতেদায়ী মাদ্রাসা)
            lower.contains("কুরআন") || lower.contains("তাজবীদ") || lower.contains("তাজভীদ") || lower.contains("quran") -> {
                SubjectThemeConfig(
                    primaryIcon = Icons.AutoMirrored.Rounded.MenuBook,
                    categoryBadge = "তাজভীদ ও তিলাওয়াত"
                )
            }
            // ২. আকাইদ ও ফিকহ (ইবতেদায়ী মাদ্রাসা)
            lower.contains("আকাইদ") || lower.contains("ফিকহ") || lower.contains("aqaid") -> {
                SubjectThemeConfig(
                    primaryIcon = Icons.Rounded.AutoStories,
                    categoryBadge = "আকাইদ ও ফিকহ"
                )
            }
            // ৩. আরবি (আদ্ দুরূসুল আরাবিয়্যাহ্ / আল-লুগাতুল আরাবিয়্যাহ্)
            lower.contains("আরবি") || lower.contains("আরাবিয়্যাহ") || lower.contains("arabic") || lower.contains("দুরূস") -> {
                SubjectThemeConfig(
                    primaryIcon = Icons.Rounded.Translate,
                    categoryBadge = "আরবি ভাষা ও পাঠ"
                )
            }
            // ৪. বাংলা (আমার বাংলা বই - স্কুল ও মাদ্রাসা)
            lower.contains("বাংলা") || lower.contains("bangla") -> {
                SubjectThemeConfig(
                    primaryIcon = Icons.Rounded.AutoStories,
                    categoryBadge = "সাহিত্য ও বর্ণমালা"
                )
            }
            // ৫. ইংরেজি (English for Today - স্কুল ও মাদ্রাসা)
            lower.contains("english") || lower.contains("ইংরেজি") -> {
                SubjectThemeConfig(
                    primaryIcon = Icons.Rounded.Translate,
                    categoryBadge = "Language & Rhymes"
                )
            }
            // ৬. প্রাথমিক গণিত (Math - স্কুল ও মাদ্রাসা)
            lower.contains("গণিত") || lower.contains("math") -> {
                SubjectThemeConfig(
                    primaryIcon = Icons.Rounded.Calculate,
                    categoryBadge = "সংখ্যা ও গণনা"
                )
            }
            // ৭. চারুপাঠ ও শিল্পকলা (Art & Craft)
            lower.contains("চারুপাঠ") || lower.contains("শিল্পকলা") || lower.contains("art") -> {
                SubjectThemeConfig(
                    primaryIcon = Icons.Rounded.Palette,
                    categoryBadge = "চিত্রকলা ও আনন্দ"
                )
            }
            // ডিফল্ট সাধারণ বিষয়
            else -> {
                SubjectThemeConfig(
                    primaryIcon = Icons.Rounded.AutoStories,
                    categoryBadge = "পাঠ্যবই ও গাইড"
                )
            }
        }
    }
}
