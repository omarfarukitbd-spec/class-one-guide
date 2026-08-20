package com.helptrickbd.class1

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.core.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // GLOBAL SYSTEM BAR MANAGEMENT:
        // Enable edge-to-edge with transparent bars. Styling is handled in Theme.kt
        enableEdgeToEdge()
        
        setContent {
            val navController = rememberNavController()
            AppTheme {
                AppNavGraph(navController = navController)
            }
        }
    }
}
