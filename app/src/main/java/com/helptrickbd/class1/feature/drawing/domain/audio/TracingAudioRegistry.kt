package com.helptrickbd.class1.feature.drawing.domain.audio

import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem

object TracingAudioRegistry {

    fun getAudioPath(item: TracingItem): String {
        return when (item.category) {
            TracingCategory.BANGLA_VOWEL -> "audio/vowels/bn_vowel_${formatIndex(item.orderIndex)}.mp3"
            TracingCategory.BANGLA_CONSONANT -> "audio/consonants/bn_cons_${formatIndex(item.orderIndex)}.mp3"
            TracingCategory.BANGLA_NUMBER -> "audio/numbers/bn_num_${formatIndex(item.orderIndex)}.mp3"
            TracingCategory.ENGLISH_ALPHABET -> "audio/english/en_alpha_${item.character.lowercase()}.mp3"
            TracingCategory.ENGLISH_NUMBER -> "audio/english_num/en_num_${item.character}.mp3"
            TracingCategory.FREE_DRAW -> ""
        }
    }

    fun getRandomPraiseAudioPath(isEnglish: Boolean): String {
        val prefix = if (isEnglish) "praise_en_" else "praise_bn_"
        val index = (1..3).random()
        return "audio/praise/${prefix}$index.mp3"
    }

    private fun formatIndex(index: Int): String {
        return if (index < 10) "0$index" else "$index"
    }
}
