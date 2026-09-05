package com.helptrickbd.class1.feature.learn_hub.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Domain model representing an interactive alphabet phonics item.
 * Supports letter-only audio for rapid alphabet soundboard
 * and sentence audio for word/sentence learning.
 */
@Immutable
data class PhonicsItem(
    val id: String,
    val letter: String,
    val name: String,
    val word: String,
    val sentence: String,
    val icon: ImageVector,
    val primaryColor: Color,
    val gradientColors: List<Color>,
    val audioAssetPath: String,
    val letterAudioPath: String? = null,
    @DrawableRes val vectorDrawableRes: Int? = null,
    val illustrationAssetPath: String? = null,
    val sentenceAudioPath: String? = null,
    val wordTokens: List<String> = emptyList(),
    val wordAudioPath: String? = null
)
