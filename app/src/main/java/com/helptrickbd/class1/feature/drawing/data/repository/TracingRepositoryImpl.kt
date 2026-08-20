package com.helptrickbd.class1.feature.drawing.data.repository

import com.helptrickbd.class1.feature.drawing.data.datasource.TracingAlphabetData
import com.helptrickbd.class1.feature.drawing.data.datasource.TracingDataSource
import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem
import com.helptrickbd.class1.feature.drawing.domain.repository.TracingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TracingRepositoryImpl @Inject constructor() : TracingRepository {

    override fun getItemsByCategory(category: TracingCategory): List<TracingItem> {
        return when (category) {
            TracingCategory.BANGLA_VOWEL -> TracingDataSource.banglaVowels
            TracingCategory.BANGLA_CONSONANT -> TracingAlphabetData.banglaConsonants
            TracingCategory.BANGLA_NUMBER -> TracingDataSource.banglaNumbers
            TracingCategory.ENGLISH_ALPHABET -> TracingAlphabetData.englishAlphabets
            TracingCategory.ENGLISH_NUMBER -> TracingDataSource.englishNumbers
            TracingCategory.FREE_DRAW -> emptyList()
        }
    }

    override fun getAllCategories(): List<TracingCategory> {
        return TracingCategory.entries
    }
}
