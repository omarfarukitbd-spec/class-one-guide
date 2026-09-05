package com.helptrickbd.class1.core.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.core.designsystem.modifiers.glassmorphism

sealed class BottomNavItem(
    val route: Screen,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(Screen.Home, "হোম", Icons.Default.Home)
    data object KidsZone : BottomNavItem(Screen.KidsZone, "কিডস জোন", Icons.Default.Face)
    data object Favorites : BottomNavItem(Screen.Favorites, "পছন্দ", Icons.Default.Bookmark)
    data object Settings : BottomNavItem(Screen.Settings, "সেটিংস", Icons.Default.Settings)
}

@Composable
fun AppBottomNavBar(
    currentRoute: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.KidsZone,
        BottomNavItem.Favorites,
        BottomNavItem.Settings
    )

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .glassmorphism(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .navigationBarsPadding(),
        containerColor = Color.Transparent,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomNavBarPreview() {
    AppTheme {
        AppBottomNavBar(
            currentRoute = Screen.Home,
            onNavigate = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomNavBarDarkPreview() {
    AppTheme(darkTheme = true) {
        AppBottomNavBar(
            currentRoute = Screen.Home,
            onNavigate = {}
        )
    }
}
