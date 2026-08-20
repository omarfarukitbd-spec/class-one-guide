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
    val glassWhite: Color,
    val glassBorder: Color,
    val deepSpace: Color
)

@Immutable
data class AppCustomBrushes(
    val liquidMain: Brush = AppBrushes.LiquidMain,
    val glassGradient: Brush = AppBrushes.GlassGradient,
    val deepSurface: Brush = AppBrushes.DeepSurface
)

val LocalAppColors = staticCompositionLocalOf { 
    AppCustomColors(
        glassWhite = Color(0x1A000000),
        glassBorder = Color(0x1A000000),
        deepSpace = Color.White
    ) 
}
val LocalAppBrushes = staticCompositionLocalOf { AppCustomBrushes() }

private val DarkColorScheme = darkColorScheme(
    primary = RoyalBlue,
    secondary = VividBlue,
    tertiary = AccentTeal,
    background = DeepNavy,
    surface = SurfaceDark,
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = RoyalBlue,
    secondary = VividBlue,
    tertiary = AccentTeal,
    background = Color.White,
    surface = AppBackgroundLight,
    onPrimary = Color.White,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Current requirement: Default to Light (White)
    // We can either respect system theme or force light for now.
    // Let's respect system theme but ensure Light is fully functional.
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val customColors = AppCustomColors(
        glassWhite = if (darkTheme) Color(0x1AFFFFFF) else Color(0x0D000000),
        glassBorder = if (darkTheme) Color(0x26FFFFFF) else Color(0x1A000000),
        deepSpace = colorScheme.background
    )
    val customBrushes = AppCustomBrushes()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val controller = WindowCompat.getInsetsController(window, view)
            
            // For Light Mode (white bg): dark icons (isAppearanceLightStatusBars = true)
            // For Dark Mode (navy bg): light icons (isAppearanceLightStatusBars = false)
            controller.isAppearanceLightStatusBars = !darkTheme
            controller.isAppearanceLightNavigationBars = !darkTheme
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
