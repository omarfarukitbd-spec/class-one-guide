package com.helptrickbd.class1.feature.learning.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

data class DrawingPath(
    val path: Path,
    val color: Color,
    val strokeWidth: Float,
    val isEraser: Boolean = false
)
