package com.helptrickbd.class1.feature.learn_hub.domain.model.quiz

import androidx.compose.runtime.Immutable

enum class QuizMode(
    val id: String,
    val titleBangla: String,
    val subtitleBangla: String
) {
    SOUND_TO_LETTER(
        id = "sound_match",
        titleBangla = "ধ্বনি শুনে বর্ণ মেলাও",
        subtitleBangla = "উচ্চারণ শুনে সঠিক বর্ণটি চিহ্নিত করো"
    ),
    PICTURE_TO_LETTER(
        id = "picture_match",
        titleBangla = "৩ডি ছবি দেখে বর্ণ চেনো",
        subtitleBangla = "ছবি দেখে প্রথম অক্ষরটি শনাক্ত করো"
    ),
    NEXT_LETTER(
        id = "next_letter",
        titleBangla = "পরের বর্ণটি বলো",
        subtitleBangla = "ধারাবাহিকতা মেনে পরের বর্ণ খুঁজে নাও"
    )
}

@Immutable
data class QuizQuestion(
    val id: String,
    val mode: QuizMode,
    val promptVoiceAsset: String? = null,
    val promptText: String,
    val targetLetter: String,
    val targetWord: String? = null,
    val illustrationAsset: String? = null,
    val sequencePrefix: String? = null,
    val options: List<String>,
    val correctIndex: Int
)

@Immutable
data class QuizGameResult(
    val mode: QuizMode,
    val totalQuestions: Int,
    val correctCount: Int,
    val score: Int,
    val starsEarned: Int,
    val accuracyPercent: Int
)

@Immutable
data class QuizStats(
    val modeId: String,
    val totalPlayed: Int,
    val totalCorrect: Int,
    val highestScore: Int,
    val totalStars: Int
)
