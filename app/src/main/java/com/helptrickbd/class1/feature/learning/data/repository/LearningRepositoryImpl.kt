package com.helptrickbd.class1.feature.learning.data.repository

import com.helptrickbd.class1.feature.learning.data.datasource.LearningDataSource
import com.helptrickbd.class1.feature.learning.domain.model.LearningCategory
import com.helptrickbd.class1.feature.learning.domain.model.LearningItem
import com.helptrickbd.class1.feature.learning.domain.repository.LearningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningRepositoryImpl @Inject constructor(
    private val dataSource: LearningDataSource
) : LearningRepository {

    override fun getItemsByCategory(category: LearningCategory): Flow<List<LearningItem>> {
        val items = when (category) {
            LearningCategory.VOWEL -> dataSource.getVowels()
            LearningCategory.CONSONANT -> dataSource.getConsonants()
            LearningCategory.NUMBER -> dataSource.getNumbers()
            LearningCategory.KAR_CHIHNO -> dataSource.getKarChihno()
            else -> emptyList()
        }
        return flowOf(items)
    }
}
