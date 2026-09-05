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
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Immutable
data class AppCustomColors(
    val glassWhite: Color,
    val glassBorder: Color,
    val deepSpace: Color,
    val topBarBackground: Color
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
        deepSpace = Color.White,
        topBarBackground = TopBarLight
    ) 
}
val LocalAppBrushes = staticCompositionLocalOf { AppCustomBrushes() }

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outlineVariant = OutlineVariantDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outlineVariant = OutlineVariantLight
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val customColors = AppCustomColors(
        glassWhite = if (darkTheme) Color(0x1AFFFFFF) else Color(0x0D000000),
        glassBorder = if (darkTheme) Color(0x26FFFFFF) else Color(0x1A000000),
        deepSpace = colorScheme.background,
        topBarBackground = if (darkTheme) TopBarDark else TopBarLight
    )
    val customBrushes = AppCustomBrushes()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val controller = WindowCompat.getInsetsController(window, view)
            
            // Fixed Logic: Status bar icons should follow the theme for accessibility
            // However, if TopBar is always dark primary (like #004700), icons must be white.
            // Our StandardTopBar uses backgroundColor, if it's primary green, we need light icons.
            controller.isAppearanceLightStatusBars = false 
            
            // Navigation bar follows the app theme correctly
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
