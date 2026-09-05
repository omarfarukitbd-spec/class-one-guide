package com.helptrickbd.class1.feature.learn_hub.domain.provider.stroke

import androidx.compose.ui.geometry.Offset
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.LetterStroke

/**
 * Authentic handwriting strokes for Bengali Consonants Part 3 (য থেকে ঁ).
 */
object BengaliConsonantStrokesPart3 {

    fun get(letter: String): List<LetterStroke>? {
        return when (letter) {
            "য" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.12f), Offset(0.22f, 0.45f), Offset(0.35f, 0.55f), Offset(0.22f, 0.88f), Offset(0.70f, 0.88f))),
                LetterStroke(3, listOf(Offset(0.70f, 0.12f), Offset(0.70f, 0.88f)))
            )
            "র" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.12f), Offset(0.18f, 0.88f))),
                LetterStroke(3, listOf(Offset(0.18f, 0.88f), Offset(0.68f, 0.88f), Offset(0.68f, 0.12f))),
                LetterStroke(4, listOf(Offset(0.42f, 0.94f), Offset(0.44f, 0.94f)))
            )
            "ল" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.40f, 0.12f), Offset(0.22f, 0.45f), Offset(0.38f, 0.65f))),
                LetterStroke(3, listOf(Offset(0.38f, 0.65f), Offset(0.55f, 0.45f), Offset(0.70f, 0.90f)))
            )
            "শ" -> listOf(
                LetterStroke(1, listOf(Offset(0.28f, 0.35f), Offset(0.18f, 0.22f), Offset(0.32f, 0.14f), Offset(0.42f, 0.32f))),
                LetterStroke(2, listOf(Offset(0.42f, 0.32f), Offset(0.52f, 0.18f), Offset(0.45f, 0.65f), Offset(0.25f, 0.88f), Offset(0.75f, 0.88f))),
                LetterStroke(3, listOf(Offset(0.75f, 0.14f), Offset(0.75f, 0.88f))),
                LetterStroke(4, listOf(Offset(0.65f, 0.14f), Offset(0.88f, 0.14f)))
            )
            "ষ" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.12f), Offset(0.25f, 0.55f), Offset(0.45f, 0.88f), Offset(0.72f, 0.88f))),
                LetterStroke(3, listOf(Offset(0.72f, 0.12f), Offset(0.72f, 0.88f))),
                LetterStroke(4, listOf(Offset(0.32f, 0.35f), Offset(0.65f, 0.75f)))
            )
            "স" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.12f), Offset(0.22f, 0.35f), Offset(0.35f, 0.50f), Offset(0.22f, 0.85f), Offset(0.70f, 0.85f))),
                LetterStroke(3, listOf(Offset(0.70f, 0.12f), Offset(0.70f, 0.85f)))
            )
            "হ" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.45f, 0.12f), Offset(0.35f, 0.40f), Offset(0.55f, 0.55f), Offset(0.35f, 0.80f), Offset(0.60f, 0.90f)))
            )
            "ড়" -> listOf(
                LetterStroke(1, listOf(Offset(0.18f, 0.15f), Offset(0.82f, 0.15f))),
                LetterStroke(2, listOf(Offset(0.48f, 0.15f), Offset(0.32f, 0.45f), Offset(0.60f, 0.65f), Offset(0.40f, 0.90f))),
                LetterStroke(3, listOf(Offset(0.50f, 0.96f), Offset(0.52f, 0.96f)))
            )
            "ঢ়" -> listOf(
                LetterStroke(1, listOf(Offset(0.18f, 0.15f), Offset(0.82f, 0.15f))),
                LetterStroke(2, listOf(Offset(0.48f, 0.15f), Offset(0.32f, 0.55f), Offset(0.55f, 0.90f), Offset(0.72f, 0.75f), Offset(0.60f, 0.65f))),
                LetterStroke(3, listOf(Offset(0.50f, 0.96f), Offset(0.52f, 0.96f)))
            )
            "য়" -> listOf(
                LetterStroke(1, listOf(Offset(0.12f, 0.12f), Offset(0.88f, 0.12f))),
                LetterStroke(2, listOf(Offset(0.38f, 0.12f), Offset(0.22f, 0.45f), Offset(0.35f, 0.55f), Offset(0.22f, 0.88f), Offset(0.70f, 0.88f))),
                LetterStroke(3, listOf(Offset(0.70f, 0.12f), Offset(0.70f, 0.88f))),
                LetterStroke(4, listOf(Offset(0.45f, 0.96f), Offset(0.47f, 0.96f)))
            )
            "ৎ" -> listOf(
                LetterStroke(1, listOf(Offset(0.45f, 0.25f), Offset(0.25f, 0.45f), Offset(0.55f, 0.65f), Offset(0.35f, 0.90f)))
            )
            "ং" -> listOf(
                LetterStroke(1, listOf(Offset(0.45f, 0.35f), Offset(0.35f, 0.25f), Offset(0.45f, 0.15f), Offset(0.55f, 0.25f), Offset(0.45f, 0.35f))),
                LetterStroke(2, listOf(Offset(0.35f, 0.60f), Offset(0.60f, 0.85f)))
            )
            "ঃ" -> listOf(
                LetterStroke(1, listOf(Offset(0.50f, 0.35f), Offset(0.40f, 0.25f), Offset(0.50f, 0.15f), Offset(0.60f, 0.25f), Offset(0.50f, 0.35f))),
                LetterStroke(2, listOf(Offset(0.50f, 0.75f), Offset(0.40f, 0.65f), Offset(0.50f, 0.55f), Offset(0.60f, 0.65f), Offset(0.50f, 0.75f)))
            )
            "ঁ" -> listOf(
                LetterStroke(1, listOf(Offset(0.28f, 0.42f), Offset(0.50f, 0.58f), Offset(0.72f, 0.42f))),
                LetterStroke(2, listOf(Offset(0.50f, 0.30f), Offset(0.52f, 0.30f)))
            )
            else -> null
        }
    }
}
