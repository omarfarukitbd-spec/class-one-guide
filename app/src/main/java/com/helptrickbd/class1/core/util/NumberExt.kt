package com.helptrickbd.class1.core.util

/**
 * Optimised digit conversion using CharArray for maximum performance.
 */
private val BANGLA_DIGITS = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

fun Int.toBanglaDigit(): String {
    val englishString = this.toString()
    val charArray = CharArray(englishString.length)
    for (i in englishString.indices) {
        val char = englishString[i]
        if (char in '0'..'9') {
            charArray[i] = BANGLA_DIGITS[char - '0']
        } else {
            charArray[i] = char
        }
    }
    return String(charArray)
}

fun String.toBanglaDigit(): String {
    val charArray = CharArray(this.length)
    for (i in this.indices) {
        val char = this[i]
        if (char in '0'..'9') {
            charArray[i] = BANGLA_DIGITS[char - '0']
        } else {
            charArray[i] = char
        }
    }
    return String(charArray)
}
