package com.helptrickbd.class1.feature.learn_hub.domain.usecase.quiz

import com.helptrickbd.class1.core.database.QuizProgressEntity
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizMode
import com.helptrickbd.class1.feature.learn_hub.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuizStatsUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    fun getProgressByMode(mode: QuizMode): Flow<QuizProgressEntity?> {
        return repository.getProgressByMode(mode)
    }

    fun getTotalStars(): Flow<Int?> {
        return repository.getTotalStars()
    }
}
