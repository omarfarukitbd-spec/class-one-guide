package com.helptrickbd.class1.feature.learn_hub.domain.provider

import com.helptrickbd.class1.feature.learn_hub.data.datasource.ConsonantsData
import com.helptrickbd.class1.feature.learn_hub.data.datasource.VowelsData
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateTracingCategory
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateTracingItem

object SlateTracingProvider {

    val freehandItem = SlateTracingItem(
        id = "freehand",
        letter = "",
        name = "মুক্ত আর্ট",
        audioPath = null,
        category = SlateTracingCategory.FREEHAND
    )

    fun getVowels(): List<SlateTracingItem> {
        return VowelsData.getVowels().map { v ->
            SlateTracingItem(
                id = v.id,
                letter = v.letter,
                name = v.name,
                audioPath = v.letterAudioPath ?: v.audioAssetPath,
                category = SlateTracingCategory.VOWELS
            )
        }
    }

    fun getConsonants(): List<SlateTracingItem> {
        return ConsonantsData.getConsonants().map { c ->
            SlateTracingItem(
                id = c.id,
                letter = c.letter,
                name = c.name,
                audioPath = c.letterAudioPath ?: c.audioAssetPath,
                category = SlateTracingCategory.CONSONANTS
            )
        }
    }

    fun getNumbers(): List<SlateTracingItem> {
        val numbers = listOf(
            Pair("১", "এক"),
            Pair("২", "দুই"),
            Pair("৩", "তিন"),
            Pair("৪", "চার"),
            Pair("৫", "পাঁচ"),
            Pair("৬", "ছয়"),
            Pair("৭", "সাত"),
            Pair("৮", "আট"),
            Pair("৯", "নয়"),
            Pair("১০", "দশ")
        )
        return numbers.mapIndexed { index, pair ->
            SlateTracingItem(
                id = "number_${index + 1}",
                letter = pair.first,
                name = pair.second,
                audioPath = "audio/numbers/number_${index + 1}.mp3",
                category = SlateTracingCategory.NUMBERS
            )
        }
    }

    fun getShapes(): List<SlateTracingItem> = SlateShapeProvider.getShapeItems()

    fun getItemsByCategory(category: SlateTracingCategory): List<SlateTracingItem> {
        return when (category) {
            SlateTracingCategory.FREEHAND -> listOf(freehandItem)
            SlateTracingCategory.VOWELS -> getVowels()
            SlateTracingCategory.CONSONANTS -> getConsonants()
            SlateTracingCategory.NUMBERS -> getNumbers()
            SlateTracingCategory.SHAPES -> getShapes()
        }
    }
}
