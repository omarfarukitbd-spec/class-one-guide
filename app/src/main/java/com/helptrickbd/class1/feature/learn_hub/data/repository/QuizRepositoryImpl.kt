package com.helptrickbd.class1.feature.learn_hub.data.repository

import com.helptrickbd.class1.core.database.QuizDao
import com.helptrickbd.class1.core.database.QuizProgressEntity
import com.helptrickbd.class1.feature.learn_hub.data.datasource.ConsonantsData
import com.helptrickbd.class1.feature.learn_hub.data.datasource.VowelsData
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizGameResult
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizMode
import com.helptrickbd.class1.feature.learn_hub.domain.model.quiz.QuizQuestion
import com.helptrickbd.class1.feature.learn_hub.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val quizDao: QuizDao
) : QuizRepository {

    private val allPhonics: List<PhonicsItem> by lazy {
        VowelsData.getVowels() + ConsonantsData.getConsonants()
    }

    private val vowels: List<PhonicsItem> by lazy {
        VowelsData.getVowels()
    }

    override fun generateQuestions(mode: QuizMode, count: Int): List<QuizQuestion> {
        return when (mode) {
            QuizMode.SOUND_TO_LETTER -> generateSoundQuestions(count)
            QuizMode.PICTURE_TO_LETTER -> generatePictureQuestions(count)
            QuizMode.NEXT_LETTER -> generateNextLetterQuestions(count)
        }
    }

    private fun generateSoundQuestions(count: Int): List<QuizQuestion> {
        val pool = allPhonics.shuffled().take(count)
        return pool.mapIndexed { idx, item ->
            val distractors = allPhonics.filter { it.letter != item.letter }.shuffled().take(3).map { it.letter }
            val options = (distractors + item.letter).shuffled()
            QuizQuestion(
                id = "sound_${idx}_${item.id}",
                mode = QuizMode.SOUND_TO_LETTER,
                promptVoiceAsset = item.letterAudioPath,
                promptText = "উচ্চারণটি শোনো এবং সঠিক বর্ণ নির্বাচন করো",
                targetLetter = item.letter,
                targetWord = item.word,
                options = options,
                correctIndex = options.indexOf(item.letter)
            )
        }
    }

    private fun generatePictureQuestions(count: Int): List<QuizQuestion> {
        val pool = allPhonics.filter { it.illustrationAssetPath != null }.shuffled().take(count)
        return pool.mapIndexed { idx, item ->
            val distractors = allPhonics.filter { it.letter != item.letter }.shuffled().take(3).map { it.letter }
            val options = (distractors + item.letter).shuffled()
            QuizQuestion(
                id = "pic_${idx}_${item.id}",
                mode = QuizMode.PICTURE_TO_LETTER,
                promptVoiceAsset = item.letterAudioPath,
                promptText = "ছবিটি দেখো এবং এর প্রথম বর্ণটি নির্বাচন করো",
                targetLetter = item.letter,
                targetWord = item.word,
                illustrationAsset = item.illustrationAssetPath,
                options = options,
                correctIndex = options.indexOf(item.letter)
            )
        }
    }

    private fun generateNextLetterQuestions(count: Int): List<QuizQuestion> {
        val questions = mutableListOf<QuizQuestion>()
        // First add vowel sequences with real voice prompts
        for (i in 0 until (vowels.size - 1)) {
            val curr = vowels[i]
            val next = vowels[i + 1]
            val distractors = allPhonics.filter { it.letter != next.letter }.shuffled().take(3).map { it.letter }
            val options = (distractors + next.letter).shuffled()
            questions.add(
                QuizQuestion(
                    id = "next_vowel_$i",
                    mode = QuizMode.NEXT_LETTER,
                    promptVoiceAsset = "audio/quiz/vowel_after_$i.mp3",
                    promptText = "${curr.letter} এর পরের বর্ণটি কী বলো তো?",
                    targetLetter = next.letter,
                    sequencePrefix = curr.letter,
                    options = options,
                    correctIndex = options.indexOf(next.letter)
                )
            )
        }
        return questions.shuffled().take(count)
    }

    override fun getProgressByMode(mode: QuizMode): Flow<QuizProgressEntity?> {
        return quizDao.getProgressByMode(mode.id)
    }

    override fun getTotalStars(): Flow<Int?> {
        return quizDao.getTotalStarsFlow()
    }

    override suspend fun saveQuizResult(result: QuizGameResult) {
        val existing = quizDao.getProgressByMode(result.mode.id).firstOrNull()
        val totalPlayed = (existing?.totalPlayed ?: 0) + 1
        val totalCorrect = (existing?.totalCorrect ?: 0) + result.correctCount
        val highestScore = maxOf(existing?.highestScore ?: 0, result.score)
        val totalStars = (existing?.totalStars ?: 0) + result.starsEarned

        quizDao.upsertProgress(
            QuizProgressEntity(
                modeId = result.mode.id,
                totalPlayed = totalPlayed,
                totalCorrect = totalCorrect,
                highestScore = highestScore,
                totalStars = totalStars,
                lastPlayedTimestamp = System.currentTimeMillis()
            )
        )
    }
}
