package com.helptrickbd.class1.feature.drawing.ui.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@Immutable
data class DrawingPath(
    val path: Path,
    val color: Color,
    val strokeWidth: Float,
    val isEraser: Boolean = false
)
