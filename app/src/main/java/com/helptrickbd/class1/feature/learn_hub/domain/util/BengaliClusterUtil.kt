package com.helptrickbd.class1.feature.learn_hub.domain.util

import java.text.BreakIterator
import java.util.Locale

/**
 * Utility to parse and split Bengali words into visually complete grapheme clusters
 * (base consonant/vowel + attached Kar marks, hasant, virama, anusvara, visarga, chandrabindu).
 * Guarantees zero isolated combining marks (◌া, ◌ি) across UI chips and puzzles.
 */
object BengaliClusterUtil {

    private val BENGALI_LOCALE = Locale.forLanguageTag("bn")

    /**
     * Splits a word into grapheme clusters using standard Bengali BreakIterator.
     * Ensures combining diacritics are always anchored to their preceding base letter.
     */
    fun splitIntoClusters(word: String): List<String> {
        if (word.isBlank()) return emptyList()
        val boundary = BreakIterator.getCharacterInstance(BENGALI_LOCALE)
        boundary.setText(word)
        val clusters = mutableListOf<String>()
        var start = boundary.first()
        var end = boundary.next()
        while (end != BreakIterator.DONE) {
            val cluster = word.substring(start, end).trim()
            if (cluster.isNotEmpty()) {
                clusters.add(cluster)
            }
            start = end
            end = boundary.next()
        }
        return clusters
    }
}
