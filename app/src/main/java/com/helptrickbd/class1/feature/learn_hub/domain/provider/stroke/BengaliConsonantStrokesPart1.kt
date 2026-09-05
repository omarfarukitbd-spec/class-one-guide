package com.helptrickbd.class1.feature.learn_hub.domain.provider.stroke

import androidx.compose.ui.geometry.Offset
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.LetterStroke

/**
 * Authentic handwriting strokes for Bengali Consonants Part 1 (ক থেকে ণ).
 */
object BengaliConsonantStrokesPart1 {

    fun get(letter: String): List<LetterStroke>? {
        return when (letter) {
            "ক" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.12f), Offset(0.18f, 0.88f), Offset(0.52f, 0.50f))),
                LetterStroke(3, listOf(Offset(0.52f, 0.50f), Offset(0.82f, 0.58f), Offset(0.64f, 0.90f)))
            )
            "খ" -> listOf(
                LetterStroke(1, listOf(Offset(0.32f, 0.35f), Offset(0.20f, 0.22f), Offset(0.35f, 0.14f), Offset(0.45f, 0.28f), Offset(0.28f, 0.58f), Offset(0.22f, 0.88f))),
                LetterStroke(2, listOf(Offset(0.22f, 0.88f), Offset(0.75f, 0.88f), Offset(0.75f, 0.14f))),
                LetterStroke(3, listOf(Offset(0.65f, 0.14f), Offset(0.88f, 0.14f)))
            )
            "গ" -> listOf(
                LetterStroke(1, listOf(Offset(0.28f, 0.60f), Offset(0.32f, 0.38f), Offset(0.48f, 0.24f), Offset(0.22f, 0.88f))),
                LetterStroke(2, listOf(Offset(0.22f, 0.88f), Offset(0.75f, 0.88f))),
                LetterStroke(3, listOf(Offset(0.75f, 0.14f), Offset(0.75f, 0.88f))),
                LetterStroke(4, listOf(Offset(0.65f, 0.14f), Offset(0.88f, 0.14f)))
            )
            "ঘ" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.32f, 0.12f), Offset(0.18f, 0.40f), Offset(0.35f, 0.52f), Offset(0.20f, 0.88f), Offset(0.75f, 0.88f))),
                LetterStroke(3, listOf(Offset(0.75f, 0.12f), Offset(0.75f, 0.88f)))
            )
            "ঙ" -> listOf(
                LetterStroke(1, listOf(Offset(0.45f, 0.18f), Offset(0.25f, 0.38f), Offset(0.55f, 0.52f), Offset(0.32f, 0.75f), Offset(0.65f, 0.88f))),
                LetterStroke(2, listOf(Offset(0.68f, 0.30f), Offset(0.78f, 0.30f)))
            )
            "চ" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.45f, 0.12f), Offset(0.25f, 0.52f), Offset(0.18f, 0.85f), Offset(0.62f, 0.85f), Offset(0.50f, 0.52f)))
            )
            "ছ" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.45f, 0.12f), Offset(0.25f, 0.45f), Offset(0.50f, 0.52f))),
                LetterStroke(3, listOf(Offset(0.30f, 0.52f), Offset(0.20f, 0.88f), Offset(0.65f, 0.88f), Offset(0.55f, 0.65f)))
            )
            "জ" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.12f), Offset(0.22f, 0.40f), Offset(0.45f, 0.65f), Offset(0.65f, 0.45f))),
                LetterStroke(3, listOf(Offset(0.65f, 0.45f), Offset(0.55f, 0.90f)))
            )
            "ঝ" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.35f, 0.12f), Offset(0.18f, 0.65f), Offset(0.42f, 0.45f), Offset(0.55f, 0.65f))),
                LetterStroke(3, listOf(Offset(0.75f, 0.12f), Offset(0.75f, 0.90f)))
            )
            "ঞ" -> listOf(
                LetterStroke(1, listOf(Offset(0.35f, 0.20f), Offset(0.20f, 0.50f), Offset(0.40f, 0.85f))),
                LetterStroke(2, listOf(Offset(0.40f, 0.45f), Offset(0.70f, 0.45f), Offset(0.65f, 0.85f)))
            )
            "ট" -> listOf(
                LetterStroke(1, listOf(Offset(0.18f, 0.22f), Offset(0.82f, 0.22f))),
                LetterStroke(2, listOf(Offset(0.45f, 0.22f), Offset(0.32f, 0.55f), Offset(0.55f, 0.88f), Offset(0.78f, 0.65f))),
                LetterStroke(3, listOf(Offset(0.50f, 0.22f), Offset(0.55f, 0.08f), Offset(0.78f, 0.06f)))
            )
            "ঠ" -> listOf(
                LetterStroke(1, listOf(Offset(0.18f, 0.22f), Offset(0.82f, 0.22f))),
                LetterStroke(2, listOf(Offset(0.50f, 0.22f), Offset(0.30f, 0.55f), Offset(0.50f, 0.88f), Offset(0.70f, 0.55f), Offset(0.50f, 0.22f))),
                LetterStroke(3, listOf(Offset(0.50f, 0.22f), Offset(0.55f, 0.08f), Offset(0.78f, 0.06f)))
            )
            "ড" -> listOf(
                LetterStroke(1, listOf(Offset(0.18f, 0.15f), Offset(0.82f, 0.15f))),
                LetterStroke(2, listOf(Offset(0.48f, 0.15f), Offset(0.32f, 0.45f), Offset(0.60f, 0.65f), Offset(0.40f, 0.90f)))
            )
            "ঢ" -> listOf(
                LetterStroke(1, listOf(Offset(0.18f, 0.15f), Offset(0.82f, 0.15f))),
                LetterStroke(2, listOf(Offset(0.48f, 0.15f), Offset(0.32f, 0.55f), Offset(0.55f, 0.90f), Offset(0.72f, 0.75f), Offset(0.60f, 0.65f)))
            )
            "ণ" -> listOf(
                LetterStroke(1, listOf(Offset(0.28f, 0.40f), Offset(0.18f, 0.28f), Offset(0.32f, 0.20f), Offset(0.45f, 0.45f), Offset(0.28f, 0.85f), Offset(0.75f, 0.85f))),
                LetterStroke(2, listOf(Offset(0.75f, 0.18f), Offset(0.75f, 0.85f))),
                LetterStroke(3, listOf(Offset(0.65f, 0.18f), Offset(0.88f, 0.18f)))
            )
            else -> null
        }
    }
}
