package com.helptrickbd.class1.feature.learning.data.datasource

import com.helptrickbd.class1.feature.learning.domain.model.LearningCategory
import com.helptrickbd.class1.feature.learning.domain.model.LearningItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningDataSource @Inject constructor() {
    
    fun getVowels() = listOf(
        LearningItem("v1", "অ", "অজগর", "অজগরটি আসছে তেড়ে", LearningCategory.VOWEL),
        LearningItem("v2", "আ", "আম", "আমটি আমি খাব পেড়ে", LearningCategory.VOWEL),
        LearningItem("v3", "ই", "ইঁদুর", "ইঁদুর ছানা ভয়ে মরে", LearningCategory.VOWEL),
        LearningItem("v4", "ঈ", "ঈগল", "ঈগল পাখি পাছে ধরে", LearningCategory.VOWEL)
    )

    fun getConsonants() = listOf(
        LearningItem("c1", "ক", "কলা", "কলা খাও মজা করে", LearningCategory.CONSONANT),
        LearningItem("c2", "খ", "খাতা", "খাতা খোলো চটপট করে", LearningCategory.CONSONANT)
    )

    fun getNumbers() = (1..10).map { 
        LearningItem("n$it", it.toString(), null, null, LearningCategory.NUMBER)
    }

    fun getKarChihno() = listOf(
        LearningItem("k1", "া", "আ-কার", null, LearningCategory.KAR_CHIHNO),
        LearningItem("k2", "ি", "ই-কার", null, LearningCategory.KAR_CHIHNO)
    )
}
