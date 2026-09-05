package com.helptrickbd.class1.feature.learn_hub.domain.provider.stroke

import androidx.compose.ui.geometry.Offset
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.LetterStroke

/**
 * Authentic handwriting strokes for Bengali Consonants Part 2 (ত থেকে ম).
 */
object BengaliConsonantStrokesPart2 {

    fun get(letter: String): List<LetterStroke>? {
        return when (letter) {
            "ত" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.45f, 0.12f), Offset(0.22f, 0.42f), Offset(0.50f, 0.88f), Offset(0.72f, 0.60f)))
            )
            "থ" -> listOf(
                LetterStroke(1, listOf(Offset(0.28f, 0.35f), Offset(0.18f, 0.22f), Offset(0.32f, 0.15f), Offset(0.40f, 0.32f))),
                LetterStroke(2, listOf(Offset(0.40f, 0.32f), Offset(0.25f, 0.65f), Offset(0.45f, 0.88f), Offset(0.75f, 0.88f))),
                LetterStroke(3, listOf(Offset(0.75f, 0.15f), Offset(0.75f, 0.88f))),
                LetterStroke(4, listOf(Offset(0.65f, 0.15f), Offset(0.88f, 0.15f)))
            )
            "দ" -> listOf(
                LetterStroke(1, listOf(Offset(0.15f, 0.12f), Offset(0.85f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.45f, 0.12f), Offset(0.45f, 0.45f), Offset(0.65f, 0.60f), Offset(0.48f, 0.90f)))
            )
            "ধ" -> listOf(
                LetterStroke(1, listOf(Offset(0.25f, 0.35f), Offset(0.42f, 0.15f), Offset(0.22f, 0.65f), Offset(0.45f, 0.88f), Offset(0.75f, 0.88f))),
                LetterStroke(2, listOf(Offset(0.75f, 0.15f), Offset(0.75f, 0.88f))),
                LetterStroke(3, listOf(Offset(0.65f, 0.15f), Offset(0.88f, 0.15f)))
            )
            "ন" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.32f, 0.50f), Offset(0.20f, 0.40f), Offset(0.32f, 0.30f), Offset(0.40f, 0.45f), Offset(0.30f, 0.75f), Offset(0.75f, 0.75f))),
                LetterStroke(3, listOf(Offset(0.75f, 0.12f), Offset(0.75f, 0.90f)))
            )
            "প" -> listOf(
                LetterStroke(1, listOf(Offset(0.25f, 0.35f), Offset(0.32f, 0.60f), Offset(0.55f, 0.60f))),
                LetterStroke(2, listOf(Offset(0.72f, 0.20f), Offset(0.72f, 0.90f))),
                LetterStroke(3, listOf(Offset(0.62f, 0.20f), Offset(0.82f, 0.20f)))
            )
            "ফ" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.45f, 0.12f), Offset(0.28f, 0.55f), Offset(0.45f, 0.90f), Offset(0.65f, 0.55f))),
                LetterStroke(3, listOf(Offset(0.65f, 0.55f), Offset(0.82f, 0.85f)))
            )
            "ব" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.12f), Offset(0.18f, 0.88f))),
                LetterStroke(3, listOf(Offset(0.18f, 0.88f), Offset(0.68f, 0.88f), Offset(0.68f, 0.12f)))
            )
            "ভ" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.42f, 0.35f), Offset(0.28f, 0.25f), Offset(0.40f, 0.12f), Offset(0.50f, 0.32f), Offset(0.32f, 0.65f), Offset(0.65f, 0.88f)))
            )
            "ম" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.12f), Offset(0.25f, 0.60f), Offset(0.18f, 0.72f), Offset(0.40f, 0.72f), Offset(0.72f, 0.72f))),
                LetterStroke(3, listOf(Offset(0.72f, 0.12f), Offset(0.72f, 0.90f)))
            )
            else -> null
        }
    }
}
