package com.helptrickbd.class1.feature.games.data.datasource

import com.helptrickbd.class1.feature.drawing.data.datasource.TracingAlphabetData
import com.helptrickbd.class1.feature.drawing.data.datasource.TracingDataSource
import com.helptrickbd.class1.feature.drawing.domain.audio.TracingAudioRegistry
import com.helptrickbd.class1.feature.games.domain.model.QuizQuestion

object QuizDataSource {
    fun generateRandomQuestions(count: Int = 10): List<QuizQuestion> {
        val allItems = (TracingDataSource.banglaVowels + TracingAlphabetData.banglaConsonants).shuffled()
        val allLetters = allItems.map { it.character }

        return allItems.take(count).mapIndexed { index, item ->
            val wrongOptions = allLetters.filter { it != item.character }.shuffled().take(3)
            val options = (wrongOptions + item.character).shuffled()
            val correctIdx = options.indexOf(item.character)

            QuizQuestion(
                id = "q_$index",
                targetLetter = item.character,
                targetWord = item.wordExample,
                audioPromptPath = TracingAudioRegistry.getAudioPath(item),
                rhymePromptPath = TracingAudioRegistry.getRhymeAudioPath(item),
                options = options,
                correctIndex = correctIdx
            )
        }
    }
}
