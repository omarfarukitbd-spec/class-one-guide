package com.helptrickbd.class1.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
            
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = false // Force light icons for dark bg
            controller.isAppearanceLightNavigationBars = false
        }
    }

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
