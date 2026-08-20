package com.helptrickbd.class1.feature.drawing.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class CelebrationState(
    val isCelebrating: Boolean = false,
    val starsEarned: Int = 3,
    val praiseMessage: String = "সাবাশ! চমৎকার হয়েছে!"
)
