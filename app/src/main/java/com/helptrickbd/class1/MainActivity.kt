package com.helptrickbd.class1

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.core.navigation.AppNavGraph
import com.helptrickbd.class1.core.settings.domain.model.ThemeMode
import com.helptrickbd.class1.core.settings.domain.repository.SettingsRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        // 100% Native Edge-to-Edge System Bar Initialization
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        
        // 100% Anti-Screenshot & Screen Capture Guard
        if (com.helptrickbd.class1.core.config.AppConfig.FLAG_SECURE_ENABLED) {
            window.setFlags(
                android.view.WindowManager.LayoutParams.FLAG_SECURE,
                android.view.WindowManager.LayoutParams.FLAG_SECURE
            )
        } else {
            window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE)
        }

        // Android 13+ (API 33) Notification Permission
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
        
        setContent {
            val themeMode by settingsRepository.getThemeMode()
                .collectAsStateWithLifecycle(initialValue = ThemeMode.SYSTEM)

            val systemDark = isSystemInDarkTheme()
            val isDarkTheme = when (themeMode) {
                ThemeMode.SYSTEM -> systemDark
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            val navController = rememberNavController()
            AppTheme(darkTheme = isDarkTheme) {
                AppNavGraph(navController = navController)
            }
        }
    }
}
