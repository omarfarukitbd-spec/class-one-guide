package com.helptrickbd.class1.feature.games.domain.model

import androidx.compose.runtime.Immutable

enum class GameType {
    HEAR_AND_PICK,
    PICTURE_MATCH,
    BALLOON_POP
}

@Immutable
data class QuizQuestion(
    val id: String,
    val targetLetter: String,       // e.g. "ক"
    val targetWord: String,         // e.g. "কলম"
    val audioPromptPath: String,    // e.g. "audio/consonants/bn_cons_01.mp3"
    val rhymePromptPath: String,    // e.g. "audio/rhymes/bn_rhyme_12.mp3"
    val options: List<String>,      // e.g. ["ক", "খ", "গ", "ঘ"]
    val correctIndex: Int
)
