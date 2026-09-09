package com.helptrickbd.class1.feature.learn_hub.domain.usecase.quiz

import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizMode
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizQuestion
import com.helptrickbd.class1.feature.learn_hub.domain.repository.QuizRepository
import javax.inject.Inject

class GetQuizQuestionsUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    operator fun invoke(mode: QuizMode, count: Int = AppConfig.QUIZ_MAX_ROUNDS): List<QuizQuestion> {
        return repository.generateQuestions(mode, count)
    }
}
