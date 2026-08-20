package com.helptrickbd.class1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.core.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // STRICT SYSTEM BAR ISOLATION: 
        // Edge-to-edge is explicitly disabled to ensure the app UI 
        // strictly starts AFTER the Status Bar and BEFORE the Navigation Bar.
        
        setContent {
            val navController = rememberNavController()
            AppTheme {
                AppNavGraph(navController = navController)
            }
        }
    }
}
