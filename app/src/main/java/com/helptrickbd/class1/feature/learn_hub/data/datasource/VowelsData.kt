package com.helptrickbd.class1.feature.learn_hub.data.datasource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem

/**
 * Single Source of Truth for the 11 Bengali Vowels (স্বরবর্ণ).
 * 100% aligned with admin-panel/js/data-phonics.js and recorded SSOT audio.
 * Grapheme-cluster / Syllable tokens prevent isolated Kar (◌া, ◌ি) rendering issues.
 */
object VowelsData {

    private fun vowel(
        id: String, letter: String, name: String, word: String, sentence: String,
        icon: ImageVector, color: Color, grad: Color,
        audio: String, letterAudio: String, index: Int, tokens: List<String>
    ) = PhonicsItem(
        id = id, letter = letter, name = name, word = word, sentence = sentence,
        icon = icon, primaryColor = color, gradientColors = listOf(color, grad),
        audioAssetPath = audio,
        letterAudioPath = letterAudio,
        illustrationAssetPath = "images/illustrations/illust_$index.jpg",
        sentenceAudioPath = audio,
        wordTokens = tokens,
        wordAudioPath = "audio/words/vowels/vowel_word_$index.mp3"
    )

    fun getVowels(): List<PhonicsItem> = listOf(
        vowel(
            "vowel_1", "অ", "স্বর অ", "অজগর", "অ তে অজগর! অজগরটি আসছে তেড়ে!",
            Icons.Rounded.Pets, Color(0xFF10B981), Color(0xFF059669),
            "audio/shorboborno/vowel_1_o.mp3", "audio/letters/vowels/vowel_1.mp3",
            1, listOf("অ", "জ", "গ", "র")
        ),
        vowel(
            "vowel_2", "আ", "স্বর আ", "আম", "আ তে আম! আমটি আমি খাব পেড়ে!",
            Icons.Rounded.Eco, Color(0xFFF59E0B), Color(0xFFD97706),
            "audio/shorboborno/vowel_2_aa.mp3", "audio/letters/vowels/vowel_2.mp3",
            2, listOf("আ", "ম")
        ),
        vowel(
            "vowel_3", "ই", "হ্রস্ব ই", "ইলিশ", "ই তে ইলিশ! ইলিশ ভাজা খেতে মজা!",
            Icons.Rounded.SetMeal, Color(0xFF06B6D4), Color(0xFF0284C7),
            "audio/shorboborno/vowel_3_i.mp3", "audio/letters/vowels/vowel_3.mp3",
            3, listOf("ই", "লি", "শ")
        ),
        vowel(
            "vowel_4", "ঈ", "দীর্ঘ ঈ", "ঈগল", "ঈ তে ঈগল! ঈগল পাখি আকাশে ওড়ে!",
            Icons.Rounded.FlutterDash, Color(0xFF8B5CF6), Color(0xFF7C3AED),
            "audio/shorboborno/vowel_4_ee.mp3", "audio/letters/vowels/vowel_4.mp3",
            4, listOf("ঈ", "গ", "ল")
        ),
        vowel(
            "vowel_5", "উ", "হ্রস্ব উ", "উট", "উ তে উট! উট চলেছে মরুর দেশে!",
            Icons.Rounded.Landscape, Color(0xFFEC4899), Color(0xFFDB2777),
            "audio/shorboborno/vowel_5_u.mp3", "audio/letters/vowels/vowel_5.mp3",
            5, listOf("উ", "ট")
        ),
        vowel(
            "vowel_6", "ঊ", "দীর্ঘ ঊ", "ঊষা", "ঊ তে ঊষা! ঊষার আলো মিষ্টি আলো!",
            Icons.Rounded.WbSunny, Color(0xFFF97316), Color(0xFFEA580C),
            "audio/shorboborno/vowel_6_oo.mp3", "audio/letters/vowels/vowel_6.mp3",
            6, listOf("ঊ", "ষা")
        ),
        vowel(
            "vowel_7", "ঋ", "ঋ", "ঋষি", "ঋ তে ঋষি! ঋষি মশাই বসেন ধ্যানে!",
            Icons.Rounded.SelfImprovement, Color(0xFF14B8A6), Color(0xFF0D9488),
            "audio/shorboborno/vowel_7_ri.mp3", "audio/letters/vowels/vowel_7.mp3",
            7, listOf("ঋ", "ষি")
        ),
        vowel(
            "vowel_8", "এ", "এ", "একতারা", "এ তে একতারা! একতারাটি বাজে বেশ!",
            Icons.Rounded.MusicNote, Color(0xFF3B82F6), Color(0xFF2563EB),
            "audio/shorboborno/vowel_8_e.mp3", "audio/letters/vowels/vowel_8.mp3",
            8, listOf("এ", "ক", "তা", "রা")
        ),
        vowel(
            "vowel_9", "ঐ", "ঐ", "ঐরাবত", "ঐ তে ঐরাবত! ঐরাবত হাতি চলে হেলেদুলে!",
            Icons.Rounded.Pets, Color(0xFF6366F1), Color(0xFF4F46E5),
            "audio/shorboborno/vowel_9_oi.mp3", "audio/letters/vowels/vowel_9.mp3",
            9, listOf("ঐ", "রা", "ব", "ত")
        ),
        vowel(
            "vowel_10", "ও", "ও", "ওল", "ও তে ওল! ওল খেলে কিন্তু ধরবে গলা!",
            Icons.Rounded.Restaurant, Color(0xFF84CC16), Color(0xFF65A30D),
            "audio/shorboborno/vowel_10_o.mp3", "audio/letters/vowels/vowel_10.mp3",
            10, listOf("ও", "ল")
        ),
        vowel(
            "vowel_11", "ঔ", "ঔ", "ঔষধ", "ঔ তে ঔষধ! ঔষধ খেলে রোগ সারে!",
            Icons.Rounded.Medication, Color(0xFFE11D48), Color(0xFFBE123C),
            "audio/shorboborno/vowel_11_ou.mp3", "audio/letters/vowels/vowel_11.mp3",
            11, listOf("ঔ", "ষ", "ধ")
        )
    )
}
