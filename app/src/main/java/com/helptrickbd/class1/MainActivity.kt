package com.helptrickbd.class1

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.core.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // STRICT SYSTEM BAR ISOLATION: 
        // Force the window to fit system windows to ensure app content 
        // strictly starts AFTER the Status Bar and BEFORE the Navigation Bar.
        WindowCompat.setDecorFitsSystemWindows(window, true)
        
        // Set solid background for system bars to prevent app colors from bleeding in
        window.statusBarColor = Color.BLACK
        window.navigationBarColor = Color.BLACK
        
        setContent {
            val navController = rememberNavController()
            AppTheme {
                AppNavGraph(navController = navController)
            }
        }
    }
}
