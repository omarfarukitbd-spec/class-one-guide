package com.helptrickbd.class1.feature.main.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.helptrickbd.class1.core.designsystem.components.AppBottomNavBar
import com.helptrickbd.class1.core.designsystem.components.ExitConfirmationDialog
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.feature.favorites.presentation.FavoritesViewModel
import com.helptrickbd.class1.feature.favorites.ui.FavoritesScreen
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.presentation.HomeViewModel
import com.helptrickbd.class1.feature.home.ui.HomeScreen
import com.helptrickbd.class1.feature.learn_hub.ui.KidsZoneScreen
import com.helptrickbd.class1.feature.settings.presentation.SettingsViewModel
import com.helptrickbd.class1.feature.settings.ui.SettingsScreen

private const val TAB_HOME = "home"
private const val TAB_KIDS_ZONE = "kids_zone"
private const val TAB_FAVORITES = "favorites"
private const val TAB_SETTINGS = "settings"

private fun screenToTabId(screen: Screen): String = when (screen) {
    is Screen.KidsZone -> TAB_KIDS_ZONE
    is Screen.Favorites -> TAB_FAVORITES
    is Screen.Settings -> TAB_SETTINGS
    else -> TAB_HOME
}

private fun tabIdToScreen(tabId: String): Screen = when (tabId) {
    TAB_KIDS_ZONE -> Screen.KidsZone
    TAB_FAVORITES -> Screen.Favorites
    TAB_SETTINGS -> Screen.Settings
    else -> Screen.Home
}

/**
 * Main application hub hosting the bottom navigation bar and primary feature tabs.
 * Preserves tab navigation history across screen transitions via rememberSaveable.
 */
@Composable
fun MainScreen(
    onBookClick: (String, String) -> Unit,
    onResumeClick: (Book) -> Unit,
    onNotificationClick: () -> Unit = {},
    onNavigateToScreen: (Screen) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var tabBackStack by rememberSaveable { mutableStateOf(listOf(TAB_HOME)) }
    val currentTab = tabIdToScreen(tabBackStack.lastOrNull() ?: TAB_HOME)
    var showExitDialog by remember { mutableStateOf(false) }

    val navigateBackTab: () -> Unit = {
        when {
            showExitDialog -> showExitDialog = false
            tabBackStack.size > 1 -> tabBackStack = tabBackStack.dropLast(1)
            else -> showExitDialog = true
        }
    }

    BackHandler(enabled = true, onBack = navigateBackTab)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            AppBottomNavBar(
                currentRoute = currentTab,
                onNavigate = { targetTab ->
                    val targetId = screenToTabId(targetTab)
                    if (targetId == TAB_HOME) {
                        tabBackStack = listOf(TAB_HOME)
                    } else if (targetId != screenToTabId(currentTab)) {
                        tabBackStack = (tabBackStack - targetId) + targetId
                    }
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
                        onResumeClick = onResumeClick,
                        onNotificationClick = onNotificationClick
                    )
                }
                is Screen.KidsZone -> {
                    KidsZoneScreen(
                        onNavigate = { route -> onNavigateToScreen(route) },
                        onBackClick = navigateBackTab
                    )
                }
                is Screen.Favorites -> {
                    val favViewModel: FavoritesViewModel = hiltViewModel()
                    FavoritesScreen(
                        viewModel = favViewModel,
                        onBookClick = onBookClick,
                        onResumeClick = onResumeClick,
                        onBackClick = navigateBackTab
                    )
                }
                is Screen.Settings -> {
                    val settingsViewModel: SettingsViewModel = hiltViewModel()
                    SettingsScreen(
                        viewModel = settingsViewModel,
                        onBackClick = navigateBackTab
                    )
                }
                else -> {}
            }
        }
    }

    if (showExitDialog) {
        ExitConfirmationDialog(
            onConfirmExit = {
                showExitDialog = false
                context.findActivity()?.finishAffinity()
            },
            onDismiss = { showExitDialog = false }
        )
    }
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
