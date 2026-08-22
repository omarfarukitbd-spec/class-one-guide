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
import com.helptrickbd.class1.feature.games.ui.GamesHubScreen
import com.helptrickbd.class1.feature.games.ui.hear_and_pick.HearAndPickScreen
import com.helptrickbd.class1.feature.games.ui.picture_match.PictureMatchScreen
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.presentation.HomeViewModel
import com.helptrickbd.class1.feature.home.ui.HomeScreen
import com.helptrickbd.class1.feature.karchihno.ui.KarChihnoScreen
import com.helptrickbd.class1.feature.settings.presentation.SettingsViewModel
import com.helptrickbd.class1.feature.settings.ui.SettingsScreen

@Composable
fun MainScreen(
    onBookClick: (String, String) -> Unit,
    onResumeClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentTab by remember { mutableStateOf<Screen>(Screen.Home) }
    var hubSubScreen by remember { mutableStateOf<Screen?>(null) }

    // If on a subscreen or non-home tab, hardware back press pops back gracefully
    BackHandler(enabled = currentTab != Screen.Home || hubSubScreen != null) {
        if (hubSubScreen != null) {
            hubSubScreen = null
        } else {
            currentTab = Screen.Home
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            AppBottomNavBar(
                currentRoute = currentTab,
                onNavigate = {
                    hubSubScreen = null
                    currentTab = it
                }
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
                    when (hubSubScreen) {
                        is Screen.DrawingSlate -> {
                            DrawingSlateScreen(onBackClick = { hubSubScreen = null })
                        }
                        is Screen.KarChihno -> {
                            KarChihnoScreen(onBackClick = { hubSubScreen = null })
                        }
                        is Screen.HearAndPick -> {
                            HearAndPickScreen(onBackClick = { hubSubScreen = null })
                        }
                        is Screen.PictureMatch -> {
                            PictureMatchScreen(onBackClick = { hubSubScreen = null })
                        }
                        else -> {
                            GamesHubScreen(
                                onNavigateToSlate = { hubSubScreen = Screen.DrawingSlate },
                                onNavigateToKarChihno = { hubSubScreen = Screen.KarChihno },
                                onNavigateToHearAndPick = { hubSubScreen = Screen.HearAndPick },
                                onNavigateToPictureMatch = { hubSubScreen = Screen.PictureMatch }
                            )
                        }
                    }
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
                    SettingsScreen(viewModel = settingsViewModel)
                }
                else -> {}
            }
        }
    }
}
