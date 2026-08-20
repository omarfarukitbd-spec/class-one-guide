package com.helptrickbd.class1.core.designsystem.theme

import androidx.compose.ui.graphics.Color

// ==========================================
// 1. Light Theme Palette (Clean & Accessible)
// ==========================================
val PrimaryLight = Color(0xFF1565C0)          // Vibrant Royal Blue
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFFE3F2FD) // Soft Ice Blue Container
val OnPrimaryContainerLight = Color(0xFF0D47A1)

val SecondaryLight = Color(0xFF00897B)        // Teal Accent
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFE0F2F1)
val OnSecondaryContainerLight = Color(0xFF004D40)

val BackgroundLight = Color(0xFFF8FAFC)       // Crisp Slate Off-White
val OnBackgroundLight = Color(0xFF0F172A)
val SurfaceLight = Color(0xFFFFFFFF)          // Pure White Card Surface
val OnSurfaceLight = Color(0xFF0F172A)        // Deep Slate Black (100% Readable)
val SurfaceVariantLight = Color(0xFFF1F5F9)   // Elevated Container Surface
val OnSurfaceVariantLight = Color(0xFF475569) // High Contrast Subtext
val OutlineVariantLight = Color(0xFFE2E8F0)   // Card Border Stroke
val TopBarLight = Color(0xFF1A237E)           // Signature Deep Royal Blue Top Bar

// ==========================================
// 2. Dark Theme Palette (High-Contrast Obsidian)
// ==========================================
val PrimaryDark = Color(0xFF64B5F6)           // Bright Vibrant Sky Blue (Pops on Dark!)
val OnPrimaryDark = Color(0xFF0A192F)         // High Contrast Dark Text on Primary
val PrimaryContainerDark = Color(0xFF1E3A8A)  // Deep Blue Container
val OnPrimaryContainerDark = Color(0xFFDBEAFE)

val SecondaryDark = Color(0xFF4DB6AC)         // Bright Teal Accent
val OnSecondaryDark = Color(0xFF003730)
val SecondaryContainerDark = Color(0xFF004D40)
val OnSecondaryContainerDark = Color(0xFFE0F2F1)

val BackgroundDark = Color(0xFF0B0F19)        // Deep Rich Obsidian Navy
val OnBackgroundDark = Color(0xFFF8FAFC)      // Crisp White
val SurfaceDark = Color(0xFF151C2E)           // Elevated Card Surface
val OnSurfaceDark = Color(0xFFF8FAFC)         // Pure White Text (100% Readable)
val SurfaceVariantDark = Color(0xFF1E293B)    // Container Surface
val OnSurfaceVariantDark = Color(0xFF94A3B8)  // High-Contrast Slate Grey Subtext
val OutlineVariantDark = Color(0xFF334155)    // Card Border Stroke
val TopBarDark = Color(0xFF151C2E)            // Seamless Elevated Navy Top Bar

// ==========================================
// 3. Backward Compatibility & Utility Aliases
// ==========================================
val RoyalBlue = Color(0xFF1A237E)
val VividBlue = Color(0xFF3F51B5)
val AccentTeal = Color(0xFF00BFA5)
val SuccessGreen = Color(0xFF00E676)
val ErrorRed = Color(0xFFFF5252)
val WarningYellow = Color(0xFFFFD740)

val GlassWhite = Color(0x1AFFFFFF)
val GlassWhiteDeep = Color(0x33FFFFFF)
val GlassBorder = Color(0x26FFFFFF)
val DeepSpace = BackgroundDark
val ElectricPurple = PrimaryDark
val CyanGlow = PrimaryDark
val SoftPink = SecondaryDark
val TextPrimary = OnSurfaceDark
val TextSecondary = OnSurfaceVariantDark
