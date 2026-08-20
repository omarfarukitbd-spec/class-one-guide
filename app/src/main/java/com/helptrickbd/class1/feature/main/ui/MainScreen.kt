package com.helptrickbd.class1.feature.main.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.helptrickbd.class1.core.designsystem.components.AppBottomNavBar
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.feature.drawing.ui.DrawingSlateScreen
import com.helptrickbd.class1.feature.favorites.presentation.FavoritesViewModel
import com.helptrickbd.class1.feature.favorites.ui.FavoritesScreen
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.presentation.HomeViewModel
import com.helptrickbd.class1.feature.home.ui.HomeScreen
import com.helptrickbd.class1.feature.settings.presentation.SettingsViewModel
import com.helptrickbd.class1.feature.settings.ui.SettingsScreen

@Composable
fun MainScreen(
    onBookClick: (String, String) -> Unit,
    onResumeClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentTab by remember { mutableStateOf<Screen>(Screen.Home) }

    // If not on Home tab, hardware back press switches back to Home tab
    BackHandler(enabled = currentTab != Screen.Home) {
        currentTab = Screen.Home
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            AppBottomNavBar(
                currentRoute = currentTab,
                onNavigate = { currentTab = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            when (currentTab) {
                is Screen.Home -> {
                    val homeViewModel: HomeViewModel = hiltViewModel()
                    HomeScreen(
                        viewModel = homeViewModel,
                        onBookClick = onBookClick,
                        onResumeClick = onResumeClick
                    )
                }
                is Screen.DrawingSlate -> {
                    DrawingSlateScreen(
                        onBackClick = { currentTab = Screen.Home }
                    )
                }
                is Screen.Favorites -> {
                    val favViewModel: FavoritesViewModel = hiltViewModel()
                    FavoritesScreen(
                        viewModel = favViewModel,
                        onBookClick = onBookClick
                    )
                }
                is Screen.Settings -> {
                    val settingsViewModel: SettingsViewModel = hiltViewModel()
                    SettingsScreen(
                        viewModel = settingsViewModel
                    )
                }
                else -> {}
            }
        }
    }
}
