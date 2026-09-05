package com.helptrickbd.class1.feature.learn_hub.domain.provider

import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.LetterStroke
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateTracingCategory
import com.helptrickbd.class1.feature.learn_hub.domain.provider.stroke.*

/**
 * Unified Repository providing authentic handwriting strokes for all Bengali characters.
 * Covers 100% of Vowels (11), Consonants (39), Numbers (10), and Shapes (6).
 */
object BengaliLetterStrokeRepository {

    fun getStrokes(letter: String, category: SlateTracingCategory, shapeId: String = ""): List<LetterStroke> {
        return when (category) {
            SlateTracingCategory.SHAPES -> {
                BengaliNumberShapeStrokes.getShape(shapeId)
            }
            SlateTracingCategory.NUMBERS -> {
                BengaliNumberShapeStrokes.getNumber(letter)
                    ?: BengaliNumberShapeStrokes.getNumber("১")!!
            }
            SlateTracingCategory.VOWELS -> {
                BengaliVowelStrokes.get(letter)
                    ?: BengaliVowelStrokes.get("অ")!!
            }
            SlateTracingCategory.CONSONANTS -> {
                BengaliConsonantStrokesPart1.get(letter)
                    ?: BengaliConsonantStrokesPart2.get(letter)
                    ?: BengaliConsonantStrokesPart3.get(letter)
                    ?: BengaliConsonantStrokesPart1.get("ক")!!
            }
            SlateTracingCategory.FREEHAND -> emptyList()
        }
    }
}
