# ==========================================
# ProGuard & R8 Optimization Rules
# Class 1 Guide • Production Release
# ==========================================

# 1. Kotlin Coroutines & Reflection
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod
-dontwarn kotlinx.coroutines.**

# 2. AndroidX Room Database & Entities
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }
-keep class * extends androidx.room.TypeConverter { *; }
-dontwarn androidx.room.paging.**

# 3. Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontwarn kotlinx.serialization.**
-keepclassmembers class * {
    *** Companion;
}
-keepclasseswithmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep @kotlinx.serialization.Serializable class * { *; }

# 4. Dagger & Hilt Dependency Injection
-dontwarn dagger.hilt.**
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }
-keep class dagger.hilt.** { *; }

# 5. Jetpack Compose Rules
-keep class androidx.compose.material.icons.** { *; }
-dontwarn androidx.compose.**

# 6. Domain Models & App Data Classes
-keep class com.helptrickbd.class1.feature.home.domain.model.** { *; }
-keep class com.helptrickbd.class1.core.database.** { *; }
-keep class com.helptrickbd.class1.core.settings.domain.model.** { *; }

# 7. Firebase Crashlytics & Analytics De-obfuscation
-keepattributes SourceFile, LineNumberTable
-keep public class * extends java.lang.Exception
-keepclassmembers enum * { *; }
-dontwarn com.google.firebase.crashlytics.**
-dontwarn com.google.firebase.analytics.**
-keep class com.helptrickbd.class1.core.analytics.** { *; }
