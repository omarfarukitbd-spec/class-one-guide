package com.helptrickbd.class1.feature.learn_hub.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.helptrickbd.class1.core.navigation.Screen

/**
 * Domain model representing an interactive learning category in Kids Zone.
 * Strictly immutable for smart Compose recomposition skipping.
 */
@Immutable
data class KidsCategory(
    val id: String,
    @StringRes val titleRes: Int,
    @StringRes val subtitleRes: Int,
    @StringRes val badgeRes: Int,
    val icon: ImageVector,
    val primaryColor: Color,
    val gradientColors: List<Color>,
    val route: Screen
)
