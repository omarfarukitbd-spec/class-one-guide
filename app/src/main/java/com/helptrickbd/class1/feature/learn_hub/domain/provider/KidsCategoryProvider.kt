package com.helptrickbd.class1.feature.learn_hub.domain.provider

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Audiotrack
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Draw
import androidx.compose.material.icons.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material.icons.rounded.Spellcheck
import androidx.compose.ui.graphics.Color
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.feature.learn_hub.domain.model.KidsCategory

/**
 * Single Source of Truth for Kids Zone instructional categories.
 * Curates child-friendly educational pathways with vibrant palettes and Material 3 vector icons.
 */
object KidsCategoryProvider {

    fun getCategories(): List<KidsCategory> = listOf(
        KidsCategory(
            id = "vowels",
            titleRes = R.string.kids_cat_vowels_title,
            subtitleRes = R.string.kids_cat_vowels_desc,
            badgeRes = R.string.kids_cat_vowels_badge,
            icon = Icons.Rounded.Spellcheck,
            primaryColor = Color(0xFF10B981),
            gradientColors = listOf(Color(0xFF10B981), Color(0xFF059669)),
            route = Screen.Phonics("vowels")
        ),
        KidsCategory(
            id = "consonants",
            titleRes = R.string.kids_cat_consonants_title,
            subtitleRes = R.string.kids_cat_consonants_desc,
            badgeRes = R.string.kids_cat_consonants_badge,
            icon = Icons.Rounded.MenuBook,
            primaryColor = Color(0xFFF59E0B),
            gradientColors = listOf(Color(0xFFF59E0B), Color(0xFFD97706)),
            route = Screen.Phonics("consonants")
        ),
        KidsCategory(
            id = "slate",
            titleRes = R.string.kids_cat_slate_title,
            subtitleRes = R.string.kids_cat_slate_desc,
            badgeRes = R.string.kids_cat_slate_badge,
            icon = Icons.Rounded.Draw,
            primaryColor = Color(0xFF06B6D4),
            gradientColors = listOf(Color(0xFF06B6D4), Color(0xFF0284C7)),
            route = Screen.Slate
        ),
        KidsCategory(
            id = "vowel_words",
            titleRes = R.string.kids_cat_vowel_words_title,
            subtitleRes = R.string.kids_cat_vowel_words_desc,
            badgeRes = R.string.kids_cat_vowel_words_badge,
            icon = Icons.Rounded.RecordVoiceOver,
            primaryColor = Color(0xFF8B5CF6),
            gradientColors = listOf(Color(0xFF8B5CF6), Color(0xFF7C3AED)),
            route = Screen.Phonics("vowels_words")
        ),
        KidsCategory(
            id = "consonant_words",
            titleRes = R.string.kids_cat_consonant_words_title,
            subtitleRes = R.string.kids_cat_consonant_words_desc,
            badgeRes = R.string.kids_cat_consonant_words_badge,
            icon = Icons.Rounded.LibraryBooks,
            primaryColor = Color(0xFFEC4899),
            gradientColors = listOf(Color(0xFFEC4899), Color(0xFFDB2777)),
            route = Screen.Phonics("consonants_words")
        ),
        KidsCategory(
            id = "rhymes",
            titleRes = R.string.kids_cat_rhymes_title,
            subtitleRes = R.string.kids_cat_rhymes_desc,
            badgeRes = R.string.kids_cat_rhymes_badge,
            icon = Icons.Rounded.Audiotrack,
            primaryColor = Color(0xFFF97316),
            gradientColors = listOf(Color(0xFFF97316), Color(0xFFEA580C)),
            route = Screen.Phonics("rhymes")
        ),
        KidsCategory(
            id = "poems",
            titleRes = R.string.kids_cat_poems_title,
            subtitleRes = R.string.kids_cat_poems_desc,
            badgeRes = R.string.kids_cat_poems_badge,
            icon = Icons.Rounded.AutoStories,
            primaryColor = Color(0xFF14B8A6),
            gradientColors = listOf(Color(0xFF14B8A6), Color(0xFF0D9488)),
            route = Screen.Phonics("poems")
        )
    )
}
