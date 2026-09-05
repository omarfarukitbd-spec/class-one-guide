package com.helptrickbd.class1.feature.learn_hub.ui.phonics

import androidx.compose.runtime.Immutable
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem

enum class PhonicsTab {
    VOWELS,
    CONSONANTS
}

enum class PhonicsDisplayMode {
    ALPHABET,
    WORDS
}

enum class PhonicsScreenMode {
    SOUNDBOARD,
    STORYBOOK,
    WORD_BUILDER
}

@Immutable
data class PhonicsUiState(
    val selectedTab: PhonicsTab = PhonicsTab.VOWELS,
    val displayMode: PhonicsDisplayMode = PhonicsDisplayMode.ALPHABET,
    val screenMode: PhonicsScreenMode = PhonicsScreenMode.SOUNDBOARD,
    val items: List<PhonicsItem> = emptyList(),
    val currentlyPlayingId: String? = null,
    val detailItem: PhonicsItem? = null
)
