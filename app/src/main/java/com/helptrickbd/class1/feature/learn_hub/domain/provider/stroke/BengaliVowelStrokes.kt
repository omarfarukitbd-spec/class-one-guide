package com.helptrickbd.class1.feature.learn_hub.domain.provider.stroke

import androidx.compose.ui.geometry.Offset
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.LetterStroke

/**
 * Authentic handwriting strokes for all 11 Bengali Vowels (স্বরবর্ণ).
 * Normalized coordinates (0.0f..1.0f) calibrated directly to font ink boundaries.
 */
object BengaliVowelStrokes {

    fun get(letter: String): List<LetterStroke>? {
        return when (letter) {
            "অ" -> listOf(
                LetterStroke(1, listOf(Offset(0.24f, 0.32f), Offset(0.16f, 0.22f), Offset(0.26f, 0.16f), Offset(0.38f, 0.26f), Offset(0.24f, 0.58f), Offset(0.42f, 0.88f), Offset(0.50f, 0.65f))),
                LetterStroke(2, listOf(Offset(0.46f, 0.62f), Offset(0.72f, 0.62f))),
                LetterStroke(3, listOf(Offset(0.72f, 0.12f), Offset(0.72f, 0.92f))),
                LetterStroke(4, listOf(Offset(0.55f, 0.12f), Offset(0.90f, 0.12f)))
            )
            "আ" -> listOf(
                LetterStroke(1, listOf(Offset(0.18f, 0.32f), Offset(0.12f, 0.22f), Offset(0.20f, 0.16f), Offset(0.30f, 0.26f), Offset(0.18f, 0.58f), Offset(0.34f, 0.88f), Offset(0.42f, 0.65f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.62f), Offset(0.58f, 0.62f))),
                LetterStroke(3, listOf(Offset(0.58f, 0.12f), Offset(0.58f, 0.92f))),
                LetterStroke(4, listOf(Offset(0.46f, 0.12f), Offset(0.92f, 0.12f))),
                LetterStroke(5, listOf(Offset(0.82f, 0.12f), Offset(0.82f, 0.92f)))
            )
            "ই" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.22f), Offset(0.88f, 0.22f))),
                LetterStroke(2, listOf(Offset(0.32f, 0.32f), Offset(0.22f, 0.26f), Offset(0.34f, 0.22f), Offset(0.52f, 0.38f), Offset(0.38f, 0.62f), Offset(0.24f, 0.86f), Offset(0.65f, 0.90f))),
                LetterStroke(3, listOf(Offset(0.50f, 0.22f), Offset(0.58f, 0.08f), Offset(0.78f, 0.06f)))
            )
            "ঈ" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.22f), Offset(0.88f, 0.22f))),
                LetterStroke(2, listOf(Offset(0.32f, 0.32f), Offset(0.22f, 0.26f), Offset(0.34f, 0.22f), Offset(0.55f, 0.40f), Offset(0.40f, 0.62f))),
                LetterStroke(3, listOf(Offset(0.40f, 0.62f), Offset(0.24f, 0.86f), Offset(0.75f, 0.90f))),
                LetterStroke(4, listOf(Offset(0.50f, 0.22f), Offset(0.58f, 0.08f), Offset(0.78f, 0.06f)))
            )
            "উ" -> listOf(
                LetterStroke(1, listOf(Offset(0.15f, 0.22f), Offset(0.80f, 0.22f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.22f), Offset(0.24f, 0.44f), Offset(0.42f, 0.62f), Offset(0.68f, 0.50f), Offset(0.50f, 0.90f))),
                LetterStroke(3, listOf(Offset(0.45f, 0.22f), Offset(0.55f, 0.08f), Offset(0.78f, 0.06f)))
            )
            "ঊ" -> listOf(
                LetterStroke(1, listOf(Offset(0.15f, 0.22f), Offset(0.80f, 0.22f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.22f), Offset(0.24f, 0.44f), Offset(0.42f, 0.62f), Offset(0.68f, 0.50f), Offset(0.50f, 0.90f))),
                LetterStroke(3, listOf(Offset(0.55f, 0.72f), Offset(0.82f, 0.82f))),
                LetterStroke(4, listOf(Offset(0.45f, 0.22f), Offset(0.55f, 0.08f), Offset(0.78f, 0.06f)))
            )
            "ঋ" -> listOf(
                LetterStroke(1, listOf(Offset(0.15f, 0.45f), Offset(0.35f, 0.18f), Offset(0.18f, 0.88f))),
                LetterStroke(2, listOf(Offset(0.28f, 0.52f), Offset(0.50f, 0.52f), Offset(0.58f, 0.72f))),
                LetterStroke(3, listOf(Offset(0.78f, 0.18f), Offset(0.78f, 0.92f))),
                LetterStroke(4, listOf(Offset(0.68f, 0.18f), Offset(0.88f, 0.18f)))
            )
            "এ" -> listOf(
                LetterStroke(1, listOf(Offset(0.35f, 0.35f), Offset(0.22f, 0.22f), Offset(0.36f, 0.12f), Offset(0.48f, 0.25f), Offset(0.30f, 0.60f), Offset(0.20f, 0.90f))),
                LetterStroke(2, listOf(Offset(0.20f, 0.90f), Offset(0.55f, 0.90f), Offset(0.82f, 0.45f)))
            )
            "ঐ" -> listOf(
                LetterStroke(1, listOf(Offset(0.32f, 0.38f), Offset(0.20f, 0.25f), Offset(0.34f, 0.16f), Offset(0.45f, 0.28f), Offset(0.28f, 0.62f), Offset(0.18f, 0.90f))),
                LetterStroke(2, listOf(Offset(0.18f, 0.90f), Offset(0.52f, 0.90f), Offset(0.78f, 0.48f))),
                LetterStroke(3, listOf(Offset(0.45f, 0.28f), Offset(0.65f, 0.08f), Offset(0.85f, 0.12f)))
            )
            "ও" -> listOf(
                LetterStroke(1, listOf(Offset(0.35f, 0.30f), Offset(0.22f, 0.18f), Offset(0.40f, 0.10f), Offset(0.62f, 0.25f), Offset(0.40f, 0.48f))),
                LetterStroke(2, listOf(Offset(0.40f, 0.48f), Offset(0.72f, 0.65f), Offset(0.50f, 0.90f), Offset(0.28f, 0.85f)))
            )
            "ঔ" -> listOf(
                LetterStroke(1, listOf(Offset(0.30f, 0.30f), Offset(0.18f, 0.18f), Offset(0.35f, 0.10f), Offset(0.54f, 0.25f), Offset(0.35f, 0.48f))),
                LetterStroke(2, listOf(Offset(0.35f, 0.48f), Offset(0.62f, 0.65f), Offset(0.44f, 0.90f), Offset(0.24f, 0.85f))),
                LetterStroke(3, listOf(Offset(0.68f, 0.45f), Offset(0.86f, 0.25f), Offset(0.78f, 0.65f)))
            )
            else -> null
        }
    }
}
