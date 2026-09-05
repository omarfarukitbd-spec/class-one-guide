package com.helptrickbd.class1.core.designsystem.theme

import androidx.compose.ui.graphics.Color

// ==========================================
// 1. Light Theme Palette (Deep Classic Green #004700 - Clean & Accessible)
// ==========================================
val PrimaryLight = Color(0xFF004700)          // Signature Deep Classic Green (#004700)
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFFE8F5E9) // Soft Mint Green Container
val OnPrimaryContainerLight = Color(0xFF004700) // Deep Classic Green Text (#004700)

val SecondaryLight = Color(0xFF004700)        // Deep Classic Green Accent (#004700)
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFE8F5E9)
val OnSecondaryContainerLight = Color(0xFF004700)

val BackgroundLight = Color(0xFFF8FAFC)       // Crisp Slate Off-White
val OnBackgroundLight = Color(0xFF0F172A)
val SurfaceLight = Color(0xFFFFFFFF)          // Pure White Card Surface
val OnSurfaceLight = Color(0xFF0F172A)        // Deep Slate Black (100% Readable)
val SurfaceVariantLight = Color(0xFFF1F5F9)   // Elevated Container Surface
val OnSurfaceVariantLight = Color(0xFF475569) // High Contrast Subtext
val OutlineVariantLight = Color(0xFFE2E8F0)   // Card Border Stroke
val TopBarLight = Color(0xFF004700)           // Deep Classic Green Top Bar (#004700)

// ==========================================
// 2. Dark Theme Palette (Electric Sky Blue & Cosmic Obsidian)
// ==========================================
val PrimaryDark = Color(0xFF0EA5E9)           // Electric Sky Blue (Exact match to screenshot!)
val OnPrimaryDark = Color(0xFFFFFFFF)         // Pure White on Sky Blue
val PrimaryContainerDark = Color(0xFF0C2B45)  // Deep Midnight Navy Container
val OnPrimaryContainerDark = Color(0xFFBAE6FD) // Ice Blue Accent Text

val SecondaryDark = Color(0xFF38BDF8)         // Cyan Glow Accent
val OnSecondaryDark = Color(0xFF082F49)
val SecondaryContainerDark = Color(0xFF075985)
val OnSecondaryContainerDark = Color(0xFFE0F2FE)

val BackgroundDark = Color(0xFF0B0F19)        // Deep Cosmic Obsidian Black
val OnBackgroundDark = Color(0xFFF8FAFC)      // Crisp Pure White
val SurfaceDark = Color(0xFF131B2C)           // Elevated Smoky Glass Card Surface
val OnSurfaceDark = Color(0xFFF8FAFC)         // Pure White Text (100% Readable)
val SurfaceVariantDark = Color(0xFF1E293B)    // Container & Chip Surface
val OnSurfaceVariantDark = Color(0xFF94A3B8)  // High-Contrast Slate Grey Subtext
val OutlineVariantDark = Color(0xFF243248)    // Subtle Card Border Stroke
val TopBarDark = Color(0xFF101726)            // Seamless Elevated Dark Top Bar

// ==========================================
// 3. Subject-Adaptive Dynamic Brand Palette
// ==========================================
val SubjectBangla = Color(0xFFF43F5E)         // Coral Ruby Rose
val SubjectBanglaContainer = Color(0xFF3B1219)
val SubjectBanglaOnContainer = Color(0xFFFECDD3)

val SubjectEnglish = Color(0xFF0EA5E9)        // Electric Sky Blue
val SubjectEnglishContainer = Color(0xFF0C2B45)
val SubjectEnglishOnContainer = Color(0xFFBAE6FD)

val SubjectMath = Color(0xFFF59E0B)           // Golden Sunset Amber
val SubjectMathContainer = Color(0xFF382306)
val SubjectMathOnContainer = Color(0xFFFDE68A)

val SubjectIslamic = Color(0xFF004700)        // Deep Classic Green
val SubjectIslamicContainer = Color(0xFFE8F5E9)
val SubjectIslamicOnContainer = Color(0xFF004700)

val SubjectArabic = Color(0xFF004700)         // Deep Classic Green
val SubjectArabicContainer = Color(0xFFE8F5E9)
val SubjectArabicOnContainer = Color(0xFF004700)

val SubjectArt = Color(0xFFA855F7)            // Neon Purple & Violet
val SubjectArtContainer = Color(0xFF2E104D)
val SubjectArtOnContainer = Color(0xFFE9D5FF)

// ==========================================
// 4. Backward Compatibility & Utility Aliases
// ==========================================
val RoyalBlue = Color(0xFF4F46E5)
val VividBlue = Color(0xFF6366F1)
val AccentTeal = Color(0xFF06B6D4)
val SuccessGreen = Color(0xFF004700)
val ErrorRed = Color(0xFFEF4444)
val WarningYellow = Color(0xFFF59E0B)

val GlassWhite = Color(0x1AFFFFFF)
val GlassWhiteDeep = Color(0x33FFFFFF)
val GlassBorder = Color(0x26FFFFFF)
val DeepSpace = BackgroundDark
val ElectricPurple = Color(0xFF818CF8)
val CyanGlow = Color(0xFF06B6D4)
val SoftPink = Color(0xFFF43F5E)
val TextPrimary = OnSurfaceDark
val TextSecondary = OnSurfaceVariantDark
