package com.helptrickbd.class1.feature.learn_hub.domain.usecase.quiz

import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizGameResult
import com.helptrickbd.class1.feature.learn_hub.domain.repository.QuizRepository
import javax.inject.Inject

class SaveQuizResultUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(result: QuizGameResult) {
        repository.saveQuizResult(result)
    }
}
