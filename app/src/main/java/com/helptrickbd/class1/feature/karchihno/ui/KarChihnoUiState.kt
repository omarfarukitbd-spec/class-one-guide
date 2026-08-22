package com.helptrickbd.class1.feature.karchihno.ui

import androidx.compose.runtime.Immutable
import com.helptrickbd.class1.feature.karchihno.domain.model.KarChihnoItem

@Immutable
data class KarChihnoUiState(
    val items: List<KarChihnoItem> = emptyList(),
    val selectedItem: KarChihnoItem? = null,
    val isSpeaking: Boolean = false,
    val isSpellingPlaying: Boolean = false
)
