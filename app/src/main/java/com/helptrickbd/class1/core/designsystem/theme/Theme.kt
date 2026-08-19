package com.helptrickbd.class1.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
data class AppCustomColors(
    val glassWhite: Color = GlassWhite,
    val glassBorder: Color = GlassBorder,
    val deepSpace: Color = DeepSpace
)

@Immutable
data class AppCustomBrushes(
    val liquidMain: Brush = AppBrushes.LiquidMain,
    val glassGradient: Brush = AppBrushes.GlassGradient,
    val deepSurface: Brush = AppBrushes.DeepSurface
)

val LocalAppColors = staticCompositionLocalOf { AppCustomColors() }
val LocalAppBrushes = staticCompositionLocalOf { AppCustomBrushes() }

private val DarkColorScheme = darkColorScheme(
    primary = ElectricPurple,
    secondary = CyanGlow,
    tertiary = SoftPink,
    background = DeepSpace,
    surface = SurfaceDark
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else DarkColorScheme // Forcing dark for the premium look

    val customColors = AppCustomColors()
    val customBrushes = AppCustomBrushes()

    CompositionLocalProvider(
        LocalAppColors provides customColors,
        LocalAppBrushes provides customBrushes
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}

object AppTheme {
    val colors: AppCustomColors
        @Composable
        get() = LocalAppColors.current

    val brushes: AppCustomBrushes
        @Composable
        get() = LocalAppBrushes.current
}
