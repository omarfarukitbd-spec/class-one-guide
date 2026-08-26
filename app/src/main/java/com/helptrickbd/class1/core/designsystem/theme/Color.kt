package com.helptrickbd.class1.core.designsystem.theme

import androidx.compose.ui.graphics.Color

// ==========================================
// 1. Light Theme Palette (Emerald Green - Clean & Accessible)
// ==========================================
val PrimaryLight = Color(0xFF059669)          // Vibrant Emerald Green
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFFECFDF5) // Soft Mint Green Container
val OnPrimaryContainerLight = Color(0xFF065F46) // Deep Forest Green Text

val SecondaryLight = Color(0xFF0D9488)        // Teal Green Accent
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFF0FDFA)
val OnSecondaryContainerLight = Color(0xFF115E59)

val BackgroundLight = Color(0xFFF8FAFC)       // Crisp Slate Off-White
val OnBackgroundLight = Color(0xFF0F172A)
val SurfaceLight = Color(0xFFFFFFFF)          // Pure White Card Surface
val OnSurfaceLight = Color(0xFF0F172A)        // Deep Slate Black (100% Readable)
val SurfaceVariantLight = Color(0xFFF1F5F9)   // Elevated Container Surface
val OnSurfaceVariantLight = Color(0xFF475569) // High Contrast Subtext
val OutlineVariantLight = Color(0xFFE2E8F0)   // Card Border Stroke
val TopBarLight = Color(0xFF064E3B)           // Signature Deep Forest Emerald Top Bar

// ==========================================
// 2. Dark Theme Palette (High-Contrast Emerald Obsidian)
// ==========================================
val PrimaryDark = Color(0xFF10B981)           // Vibrant Emerald Green (Crisp on Dark!)
val OnPrimaryDark = Color(0xFF022C22)         // High Contrast Dark on Emerald
val PrimaryContainerDark = Color(0xFF064E3B)  // Deep Emerald Container
val OnPrimaryContainerDark = Color(0xFF6EE7B7) // Crisp Mint Accent Text

val SecondaryDark = Color(0xFF14B8A6)         // Bright Teal Accent
val OnSecondaryDark = Color(0xFF042F2E)
val SecondaryContainerDark = Color(0xFF115E59)
val OnSecondaryContainerDark = Color(0xFF99F6E4)

val BackgroundDark = Color(0xFF0B111E)        // Deep Rich Obsidian Navy
val OnBackgroundDark = Color(0xFFF8FAFC)      // Crisp Pure White
val SurfaceDark = Color(0xFF151E2E)           // Elevated Card Surface
val OnSurfaceDark = Color(0xFFF8FAFC)         // Pure White Text (100% Readable)
val SurfaceVariantDark = Color(0xFF1E2A3C)    // Container Surface
val OnSurfaceVariantDark = Color(0xFF94A3B8)  // High-Contrast Slate Grey Subtext
val OutlineVariantDark = Color(0xFF2A384C)    // Card Border Stroke
val TopBarDark = Color(0xFF151E2E)            // Seamless Elevated Top Bar

// ==========================================
// 3. Backward Compatibility & Utility Aliases
// ==========================================
val RoyalBlue = Color(0xFF064E3B)
val VividBlue = Color(0xFF059669)
val AccentTeal = Color(0xFF10B981)
val SuccessGreen = Color(0xFF10B981)
val ErrorRed = Color(0xFFEF4444)
val WarningYellow = Color(0xFFF59E0B)

val GlassWhite = Color(0x1AFFFFFF)
val GlassWhiteDeep = Color(0x33FFFFFF)
val GlassBorder = Color(0x26FFFFFF)
val DeepSpace = BackgroundDark
val ElectricPurple = PrimaryDark
val CyanGlow = PrimaryDark
val SoftPink = SecondaryDark
val TextPrimary = OnSurfaceDark
val TextSecondary = OnSurfaceVariantDark
