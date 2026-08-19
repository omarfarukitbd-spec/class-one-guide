package com.helptrickbd.class1.core.designsystem.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AppBrushes {
    val LiquidMain = Brush.linearGradient(
        colors = listOf(ElectricPurple, CyanGlow)
    )

    val GlassGradient = Brush.verticalGradient(
        colors = listOf(GlassWhite, Color.Transparent)
    )

    val SoftSunset = Brush.horizontalGradient(
        colors = listOf(SoftPink, ElectricPurple)
    )

    val DeepSurface = Brush.radialGradient(
        colors = listOf(SurfaceDark, DeepSpace)
    )
}
