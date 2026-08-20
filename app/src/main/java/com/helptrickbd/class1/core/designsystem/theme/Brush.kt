package com.helptrickbd.class1.core.designsystem.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AppBrushes {
    val LiquidMain = Brush.linearGradient(
        colors = listOf(PrimaryLight, SecondaryLight)
    )

    val GlassGradient = Brush.verticalGradient(
        colors = listOf(Color(0x26FFFFFF), Color.Transparent)
    )

    val SoftSunset = Brush.horizontalGradient(
        colors = listOf(SecondaryDark, PrimaryDark)
    )

    val DeepSurface = Brush.radialGradient(
        colors = listOf(SurfaceDark, BackgroundDark)
    )
}
