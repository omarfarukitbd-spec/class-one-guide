package com.helptrickbd.class1.feature.drawing.data.datasource

import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem

object TracingDataSource {

    val banglaVowels = listOf(
        TracingItem("v1", "অ", "অজগর", "অজগর আসছে তেড়ে", TracingCategory.BANGLA_VOWEL, 1),
        TracingItem("v2", "আ", "আম", "আমটি আমি খাব পেড়ে", TracingCategory.BANGLA_VOWEL, 2),
        TracingItem("v3", "ই", "ইঁদুর", "ইঁদুর ছানা ভয়ে মরে", TracingCategory.BANGLA_VOWEL, 3),
        TracingItem("v4", "ঈ", "ঈগল", "ঈগল পাখি পাছে ধরে", TracingCategory.BANGLA_VOWEL, 4),
        TracingItem("v5", "উ", "উট", "উট চলেছে মুখটি তুলে", TracingCategory.BANGLA_VOWEL, 5),
        TracingItem("v6", "ঊ", "ঊষা", "ঊষা হাসে পুব আকাশে", TracingCategory.BANGLA_VOWEL, 6),
        TracingItem("v7", "ঋ", "ঋষি", "ঋষি মশাই বসেন পূজায়", TracingCategory.BANGLA_VOWEL, 7),
        TracingItem("v8", "এ", "একতারা", "একতারাটি বাজে বেশ", TracingCategory.BANGLA_VOWEL, 8),
        TracingItem("v9", "ঐ", "ঐরাবত", "ঐরাবতটি দেখতে বেশ", TracingCategory.BANGLA_VOWEL, 9),
        TracingItem("v10", "ও", "ওল", "ওল খেলে ধরবে গলা", TracingCategory.BANGLA_VOWEL, 10),
        TracingItem("v11", "ঔ", "ঔষধ", "ঔষধ খেতে মিছে বলা", TracingCategory.BANGLA_VOWEL, 11)
    )

    val banglaNumbers = listOf(
        TracingItem("bn1", "১", "এক", "একতারা", TracingCategory.BANGLA_NUMBER, 1),
        TracingItem("bn2", "২", "দুই", "দুইটি পাখি", TracingCategory.BANGLA_NUMBER, 2),
        TracingItem("bn3", "৩", "তিন", "তিনটি তারা", TracingCategory.BANGLA_NUMBER, 3),
        TracingItem("bn4", "৪", "চার", "চারটি ফুল", TracingCategory.BANGLA_NUMBER, 4),
        TracingItem("bn5", "৫", "পাঁচ", "পাঁচটি ফল", TracingCategory.BANGLA_NUMBER, 5),
        TracingItem("bn6", "৬", "ছয়", "ছয়টি ঋতু", TracingCategory.BANGLA_NUMBER, 6),
        TracingItem("bn7", "৭", "সাত", "সাতটি রং", TracingCategory.BANGLA_NUMBER, 7),
        TracingItem("bn8", "৮", "আট", "আটটি পাতা", TracingCategory.BANGLA_NUMBER, 8),
        TracingItem("bn9", "৯", "নয়", "নয়টি মাছ", TracingCategory.BANGLA_NUMBER, 9),
        TracingItem("bn10", "১০", "দশ", "দশটি কলম", TracingCategory.BANGLA_NUMBER, 10)
    )

    val englishNumbers = listOf(
        TracingItem("en1", "1", "One", "1 Star", TracingCategory.ENGLISH_NUMBER, 1),
        TracingItem("en2", "2", "Two", "2 Birds", TracingCategory.ENGLISH_NUMBER, 2),
        TracingItem("en3", "3", "Three", "3 Balls", TracingCategory.ENGLISH_NUMBER, 3),
        TracingItem("en4", "4", "Four", "4 Cups", TracingCategory.ENGLISH_NUMBER, 4),
        TracingItem("en5", "5", "Five", "5 Apples", TracingCategory.ENGLISH_NUMBER, 5),
        TracingItem("en6", "6", "Six", "6 Flowers", TracingCategory.ENGLISH_NUMBER, 6),
        TracingItem("en7", "7", "Seven", "7 Trees", TracingCategory.ENGLISH_NUMBER, 7),
        TracingItem("en8", "8", "Eight", "8 Leaves", TracingCategory.ENGLISH_NUMBER, 8),
        TracingItem("en9", "9", "Nine", "9 Cars", TracingCategory.ENGLISH_NUMBER, 9),
        TracingItem("en10", "10", "Ten", "10 Pencils", TracingCategory.ENGLISH_NUMBER, 10)
    )
}
