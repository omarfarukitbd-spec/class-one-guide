package com.helptrickbd.class1.feature.karchihno.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class KarChihnoItem(
    val id: String,
    val sign: String,           // e.g. "া"
    val name: String,           // e.g. "আ-কার"
    val fullVowel: String,      // e.g. "আ"
    val exampleWord: String,    // e.g. "পাখা"
    val spellSentence: String,  // e.g. "প-এ আ-কার পা, খ-এ আ-কার খা — পাখা"
    val signAudioPath: String,  // e.g. "audio/karchihno/kc_akar.mp3"
    val spellAudioPath: String, // e.g. "audio/karchihno/kc_spell_pa_akar.mp3"
    val wordAudioPath: String,  // e.g. "audio/karchihno/word_pakha.mp3"
    val orderIndex: Int
)
