package com.helptrickbd.class1.feature.learn_hub.domain.repository

import com.helptrickbd.class1.core.database.QuizProgressEntity
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizGameResult
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizMode
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizQuestion
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun generateQuestions(mode: QuizMode, count: Int): List<QuizQuestion>
    fun getProgressByMode(mode: QuizMode): Flow<QuizProgressEntity?>
    fun getTotalStars(): Flow<Int?>
    suspend fun saveQuizResult(result: QuizGameResult)
}
