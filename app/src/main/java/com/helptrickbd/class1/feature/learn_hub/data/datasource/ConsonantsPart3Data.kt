package com.helptrickbd.class1.feature.learn_hub.data.datasource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.helptrickbd.class1.R
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem

/**
 * Single Source of Truth for Bengali Consonants (ড় to ঁ).
 * 100% aligned with admin-panel/js/data-phonics.js.
 * Grapheme-cluster / Syllable tokens prevent isolated Kar (◌া, ◌ি) rendering issues.
 */
object ConsonantsPart3Data {

    private fun c(
        id: String, letter: String, name: String, word: String, sentence: String,
        icon: ImageVector, color: Color, grad: Color, audioId: Int, tokens: List<String>,
        vectorRes: Int? = null
    ) = PhonicsItem(
        id = id, letter = letter, name = name, word = word, sentence = sentence,
        icon = icon, primaryColor = color, gradientColors = listOf(color, grad),
        audioAssetPath = "audio/banjonborno/$letter.mp3",
        letterAudioPath = "audio/letters/consonants/consonant_$audioId.mp3",
        illustrationAssetPath = "images/illustrations/illust_${11 + audioId}.jpg",
        sentenceAudioPath = "audio/banjonborno/$letter.mp3",
        wordTokens = tokens,
        vectorDrawableRes = vectorRes,
        wordAudioPath = "audio/words/consonants/consonant_word_$audioId.mp3"
    )

    val items: List<PhonicsItem> = listOf(
        c("c_33", "ড়", "ড-এ শূন্য ড়", "পাহাড়", "ড় তে পাহাড়! পাহাড় অনেক উঁচু হয়!", Icons.Rounded.Landscape, Color(0xFF6366F1), Color(0xFF4F46E5), 33, listOf("পা", "হা", "ড়")),
        c("c_34", "ঢ়", "ঢ-এ শূন্য ঢ়", "আষাঢ়", "ঢ় তে আষাঢ়! আষাঢ় মাসে বৃষ্টি হয়!", Icons.Rounded.CloudQueue, Color(0xFF10B981), Color(0xFF059669), 34, listOf("আ", "ষা", "ঢ়")),
        c("c_35", "য়", "অন্তঃস্থ য়", "আয়না", "য় তে আয়না! আয়নায় আমরা মুখ দেখি!", Icons.Rounded.CropPortrait, Color(0xFFF59E0B), Color(0xFFD97706), 35, listOf("আ", "য়", "না")),
        c("c_36", "ৎ", "খণ্ডিত ত", "মৎস্য", "ৎ তে মৎস্য! মৎস্য মানে হলো মাছ!", Icons.Rounded.SetMeal, Color(0xFF3B82F6), Color(0xFF2563EB), 36, listOf("মৎ", "স্য")),
        c("c_37", "ং", "অনুস্বার", "শিং", "অনুস্বার তে শিং! হরিণের শিং বাঁকা!", Icons.Rounded.Pets, Color(0xFF8B5CF6), Color(0xFF7C3AED), 37, listOf("শিং"), vectorRes = R.drawable.ic_borno_anusvar),
        c("c_38", "ঃ", "বিসর্গ", "দুঃখ", "বিসর্গ তে দুঃখ! দুঃখীর মুখে হাসি ফোটাই!", Icons.Rounded.SentimentSatisfiedAlt, Color(0xFFEC4899), Color(0xFFDB2777), 38, listOf("দুঃ", "খ"), vectorRes = R.drawable.ic_borno_bisorgo),
        c("c_39", "ঁ", "চন্দ্রবিন্দু", "চাঁদ", "চন্দ্রবিন্দু তে চাঁদ! রাতের আকাশে ওঠে চাঁদ!", Icons.Rounded.NightsStay, Color(0xFF06B6D4), Color(0xFF0284C7), 39, listOf("চাঁ", "দ"), vectorRes = R.drawable.ic_borno_chandrabindu)
    )
}
