package com.helptrickbd.class1.feature.learn_hub.data.datasource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem

/**
 * Single Source of Truth for Bengali Consonants (ত to হ).
 * 100% aligned with admin-panel/js/data-phonics.js.
 * Grapheme-cluster / Syllable tokens prevent isolated Kar (◌া, ◌ি) rendering issues.
 */
object ConsonantsPart2Data {

    private fun c(
        id: String, letter: String, name: String, word: String, sentence: String,
        icon: ImageVector, color: Color, grad: Color, audioId: Int, tokens: List<String>
    ) = PhonicsItem(
        id = id, letter = letter, name = name, word = word, sentence = sentence,
        icon = icon, primaryColor = color, gradientColors = listOf(color, grad),
        audioAssetPath = "audio/banjonborno/$letter.mp3",
        letterAudioPath = "audio/letters/consonants/consonant_$audioId.mp3",
        illustrationAssetPath = "images/illustrations/illust_${11 + audioId}.jpg",
        sentenceAudioPath = "audio/banjonborno/$letter.mp3",
        wordTokens = tokens,
        wordAudioPath = "audio/words/consonants/consonant_word_$audioId.mp3"
    )

    val items: List<PhonicsItem> = listOf(
        c("c_16", "ত", "ত", "তরমুজ", "ত তে তরমুজ! তরমুজ খেতে ভারি মজা!", Icons.Rounded.Eco, Color(0xFFEC4899), Color(0xFFDB2777), 16, listOf("ত", "র", "মু", "জ")),
        c("c_17", "থ", "থ", "থালা", "থ তে থালা! থালায় আমরা খাবার খাই!", Icons.Rounded.DinnerDining, Color(0xFF06B6D4), Color(0xFF0284C7), 17, listOf("থা", "লা")),
        c("c_18", "দ", "দ", "দোয়েল", "দ তে দোয়েল! দোয়েল আমাদের জাতীয় পাখি!", Icons.Rounded.FlutterDash, Color(0xFFF97316), Color(0xFFEA580C), 18, listOf("দো", "য়ে", "ল")),
        c("c_19", "ধ", "ধ", "ধান", "ধ তে ধান! ধান থেকে চাল হয়!", Icons.Rounded.Grass, Color(0xFF14B8A6), Color(0xFF0D9488), 19, listOf("ধা", "ন")),
        c("c_20", "ন", "দন্ত্য ন", "নদী", "ন তে নদী! নদীতে নৌকা চলে!", Icons.Rounded.Water, Color(0xFF84CC16), Color(0xFF65A30D), 20, listOf("ন", "দী")),
        c("c_21", "প", "প", "পাখি", "প তে পাখি! পাখি ওড়ে নীল আকাশে!", Icons.Rounded.FlutterDash, Color(0xFFE11D48), Color(0xFFBE123C), 21, listOf("পা", "খি")),
        c("c_22", "ফ", "ফ", "ফুল", "ফ তে ফুল! ফুল ফোটে গাছে গাছে!", Icons.Rounded.LocalFlorist, Color(0xFF6366F1), Color(0xFF4F46E5), 22, listOf("ফু", "ল")),
        c("c_23", "ব", "ব", "বই", "ব তে বই! বই পড়লে জ্ঞান বাড়ে!", Icons.Rounded.MenuBook, Color(0xFF10B981), Color(0xFF059669), 23, listOf("ব", "ই")),
        c("c_24", "ভ", "ভ", "ভালুক", "ভ তে ভালুক! ভালুক নাচে হেলেদুলে!", Icons.Rounded.Pets, Color(0xFFF59E0B), Color(0xFFD97706), 24, listOf("ভা", "লু", "ক")),
        c("c_25", "ম", "ম", "মাছ", "ম তে মাছ! মাছ থাকে নদীর জলে!", Icons.Rounded.SetMeal, Color(0xFF3B82F6), Color(0xFF2563EB), 25, listOf("মা", "ছ")),
        c("c_26", "য", "অন্তঃস্থ য", "যাতা", "য তে যাতা! যাতা ঘোরে হাতের জোরে!", Icons.Rounded.Settings, Color(0xFF8B5CF6), Color(0xFF7C3AED), 26, listOf("যা", "তা")),
        c("c_27", "র", "র", "রথ", "র তে রথ! রথ টানে মেলাতে!", Icons.Rounded.Festival, Color(0xFFEC4899), Color(0xFFDB2777), 27, listOf("র", "থ")),
        c("c_28", "ল", "ল", "লাটিম", "ল তে লাটিম! লাটিম ঘোরে বনবন!", Icons.Rounded.Toys, Color(0xFF06B6D4), Color(0xFF0284C7), 28, listOf("লা", "টি", "ম")),
        c("c_29", "শ", "তালব্য শ", "শাপলা", "শ তে শাপলা! শাপলা আমাদের জাতীয় ফুল!", Icons.Rounded.LocalFlorist, Color(0xFFF97316), Color(0xFFEA580C), 29, listOf("শা", "প", "লা")),
        c("c_30", "ষ", "মূর্ধন্য ষ", "ষাঁড়", "ষ তে ষাঁড়! ষাঁড় ছুটেছে মাঠের পাড়ে!", Icons.Rounded.Pets, Color(0xFF14B8A6), Color(0xFF0D9488), 30, listOf("ষাঁ", "ড়")),
        c("c_31", "স", "দন্ত্য স", "সিংহ", "স তে সিংহ! সিংহ হলো বনের রাজা!", Icons.Rounded.Pets, Color(0xFF84CC16), Color(0xFF65A30D), 31, listOf("সিং", "হ")),
        c("c_32", "হ", "হ", "হাঁস", "হ তে হাঁস! হাঁস ভাসে দিঘির জলে!", Icons.Rounded.FlutterDash, Color(0xFFE11D48), Color(0xFFBE123C), 32, listOf("হাঁ", "স"))
    )
}
