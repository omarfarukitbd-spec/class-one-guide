package com.helptrickbd.class1.feature.learning.domain.repository

import com.helptrickbd.class1.feature.learning.domain.model.LearningCategory
import com.helptrickbd.class1.feature.learning.domain.model.LearningItem
import kotlinx.coroutines.flow.Flow

interface LearningRepository {
    fun getItemsByCategory(category: LearningCategory): Flow<List<LearningItem>>
}
