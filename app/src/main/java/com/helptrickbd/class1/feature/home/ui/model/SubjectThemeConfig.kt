package com.helptrickbd.class1.feature.home.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class SubjectThemeConfig(
    val primaryColor: Color,
    val containerColor: Color,
    val onContainerColor: Color,
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
                    primaryColor = Color(0xFF00897B),
                    containerColor = Color(0xFFE0F2F1),
                    onContainerColor = Color(0xFF004D40),
                    primaryIcon = Icons.AutoMirrored.Rounded.MenuBook,
                    categoryBadge = "তাজভীদ ও তিলাওয়াত"
                )
            }
            // ২. আকাইদ ও ফিকহ (ইবতেদায়ী মাদ্রাসা)
            lower.contains("আকাইদ") || lower.contains("ফিকহ") || lower.contains("aqaid") -> {
                SubjectThemeConfig(
                    primaryColor = Color(0xFF2E7D32),
                    containerColor = Color(0xFFE8F5E9),
                    onContainerColor = Color(0xFF1B5E20),
                    primaryIcon = Icons.Rounded.AutoStories,
                    categoryBadge = "আকাইদ ও ফিকহ"
                )
            }
            // ৩. আরবি (আদ্ দুরূসুল আরাবিয়্যাহ্ / আল-লুগাতুল আরাবিয়্যাহ্)
            lower.contains("আরবি") || lower.contains("আরাবিয়্যাহ") || lower.contains("arabic") || lower.contains("দুরূস") -> {
                SubjectThemeConfig(
                    primaryColor = Color(0xFF3949AB),
                    containerColor = Color(0xFFE8EAF6),
                    onContainerColor = Color(0xFF1A237E),
                    primaryIcon = Icons.Rounded.Translate,
                    categoryBadge = "আরবি ভাষা ও পাঠ"
                )
            }
            // ৪. বাংলা (আমার বাংলা বই - স্কুল ও মাদ্রাসা)
            lower.contains("বাংলা") || lower.contains("bangla") -> {
                SubjectThemeConfig(
                    primaryColor = Color(0xFF00897B),
                    containerColor = Color(0xFFE0F2F1),
                    onContainerColor = Color(0xFF004D40),
                    primaryIcon = Icons.Rounded.AutoStories,
                    categoryBadge = "সাহিত্য ও বর্ণমালা"
                )
            }
            // ৫. ইংরেজি (English for Today - স্কুল ও মাদ্রাসা)
            lower.contains("english") || lower.contains("ইংরেজি") -> {
                SubjectThemeConfig(
                    primaryColor = Color(0xFF3949AB),
                    containerColor = Color(0xFFE8EAF6),
                    onContainerColor = Color(0xFF1A237E),
                    primaryIcon = Icons.Rounded.Translate,
                    categoryBadge = "Language & Rhymes"
                )
            }
            // ৬. প্রাথমিক গণিত (Math - স্কুল ও মাদ্রাসা)
            lower.contains("গণিত") || lower.contains("math") -> {
                SubjectThemeConfig(
                    primaryColor = Color(0xFFFB8C00),
                    containerColor = Color(0xFFFFF3E0),
                    onContainerColor = Color(0xFFE65100),
                    primaryIcon = Icons.Rounded.Calculate,
                    categoryBadge = "সংখ্যা ও গণনা"
                )
            }
            // ৭. চারুপাঠ ও শিল্পকলা (Art & Craft)
            lower.contains("চারুপাঠ") || lower.contains("শিল্পকলা") || lower.contains("art") -> {
                SubjectThemeConfig(
                    primaryColor = Color(0xFFE91E63),
                    containerColor = Color(0xFFFCE4EC),
                    onContainerColor = Color(0xFF880E4F),
                    primaryIcon = Icons.Rounded.Palette,
                    categoryBadge = "চিত্রকলা ও আনন্দ"
                )
            }
            // ডিফল্ট সাধারণ বিষয়
            else -> {
                SubjectThemeConfig(
                    primaryColor = Color(0xFF1976D2),
                    containerColor = Color(0xFFE3F2FD),
                    onContainerColor = Color(0xFF0D47A1),
                    primaryIcon = Icons.Rounded.AutoStories,
                    categoryBadge = "পাঠ্যবই ও গাইড"
                )
            }
        }
    }
}
