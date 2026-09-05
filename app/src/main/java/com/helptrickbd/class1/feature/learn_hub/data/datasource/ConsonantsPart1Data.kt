package com.helptrickbd.class1.feature.learn_hub.data.datasource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem

/**
 * Single Source of Truth for Bengali Consonants (ক to ণ).
 * 100% aligned with admin-panel/js/data-phonics.js.
 * Grapheme-cluster / Syllable tokens prevent isolated Kar (◌া, ◌ি) rendering issues.
 */
object ConsonantsPart1Data {

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
        c("c_1", "ক", "ক", "কলম", "ক তে কলম! কলম দিয়ে লেখা যায়!", Icons.Rounded.Create, Color(0xFFF59E0B), Color(0xFFD97706), 1, listOf("ক", "ল", "ম")),
        c("c_2", "খ", "খ", "খাতা", "খ তে খাতা! খাতায় আমরা ছবি আঁকি!", Icons.Rounded.MenuBook, Color(0xFF3B82F6), Color(0xFF2563EB), 2, listOf("খা", "তা")),
        c("c_3", "গ", "গ", "গরু", "গ তে গরু! গরু আমাদের দুধ দেয়!", Icons.Rounded.Pets, Color(0xFF10B981), Color(0xFF059669), 3, listOf("গ", "রু")),
        c("c_4", "ঘ", "ঘ", "ঘড়ি", "ঘ তে ঘড়ি! ঘড়িতে আমরা সময় দেখি!", Icons.Rounded.WatchLater, Color(0xFF8B5CF6), Color(0xFF7C3AED), 4, listOf("ঘ", "ড়ি")),
        c("c_5", "ঙ", "ঙ", "ব্যাঙ", "ঙ তে ব্যাঙ! ব্যাঙ ডাকে ঘ্যাঙর ঘ্যাঙ!", Icons.Rounded.PestControl, Color(0xFFEC4899), Color(0xFFDB2777), 5, listOf("ব্যা", "ঙ")),
        c("c_6", "চ", "চ", "চশমা", "চ তে চশমা! চশমা পরেন দাদুভাই!", Icons.Rounded.Visibility, Color(0xFF06B6D4), Color(0xFF0284C7), 6, listOf("চ", "শ", "মা")),
        c("c_7", "ছ", "ছ", "ছাতা", "ছ তে ছাতা! ছাতা লাগে বৃষ্টি হলে!", Icons.Rounded.BeachAccess, Color(0xFFF97316), Color(0xFFEA580C), 7, listOf("ছা", "তা")),
        c("c_8", "জ", "জ", "জাহাজ", "জ তে জাহাজ! জাহাজ চলে সাগর জলে!", Icons.Rounded.DirectionsBoat, Color(0xFF14B8A6), Color(0xFF0D9488), 8, listOf("জা", "হা", "জ")),
        c("c_9", "ঝ", "ঝ", "ঝুড়ি", "ঝ তে ঝুড়ি! ঝুড়ি ভরা ফলমূল!", Icons.Rounded.ShoppingBasket, Color(0xFF84CC16), Color(0xFF65A30D), 9, listOf("ঝু", "ড়ি")),
        c("c_10", "ঞ", "ঞ", "মিঞা", "ঞ তে মিঞা! মিঞা ভাই গায় গান!", Icons.Rounded.Person, Color(0xFFE11D48), Color(0xFFBE123C), 10, listOf("মি", "ঞা")),
        c("c_11", "ট", "ট", "টিয়া", "ট তে টিয়া! টিয়া পাখির ঠোঁট লাল!", Icons.Rounded.FlutterDash, Color(0xFF6366F1), Color(0xFF4F46E5), 11, listOf("টি", "য়া")),
        c("c_12", "ঠ", "ঠ", "ঠেলাগাড়ি", "ঠ তে ঠেলাগাড়ি! ঠেলাগাড়ি টানে ভাই!", Icons.Rounded.ShoppingCart, Color(0xFF10B981), Color(0xFF059669), 12, listOf("ঠে", "লা", "গা", "ড়ি")),
        c("c_13", "ড", "ড", "ডাব", "ড তে ডাব! ডাবের পানি খেতে মজা!", Icons.Rounded.WaterDrop, Color(0xFFF59E0B), Color(0xFFD97706), 13, listOf("ডা", "ব")),
        c("c_14", "ঢ", "ঢ", "ঢোল", "ঢ তে ঢোল! ঢোল বাজে তালে তালে!", Icons.Rounded.Speaker, Color(0xFF3B82F6), Color(0xFF2563EB), 14, listOf("ঢো", "ল")),
        c("c_15", "ণ", "মূর্ধন্য ণ", "হরিণ", "ণ তে হরিণ! হরিণ থাকে গহীন বনে!", Icons.Rounded.Pets, Color(0xFF8B5CF6), Color(0xFF7C3AED), 15, listOf("হ", "রি", "ণ"))
    )
}
