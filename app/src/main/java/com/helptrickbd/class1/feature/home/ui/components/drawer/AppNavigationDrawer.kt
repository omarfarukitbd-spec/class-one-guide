package com.helptrickbd.class1.feature.home.ui.components.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.core.designsystem.modifiers.bounceClick

@Composable
fun AppNavigationDrawer(
    onOfflineBooksClick: () -> Unit,
    onCheckUpdateClick: () -> Unit,
    onFbGroupClick: () -> Unit,
    onShareClick: () -> Unit,
    onRateClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onAboutClick: () -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier.width(320.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerContentColor = MaterialTheme.colorScheme.onSurface,
        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            DrawerHeader()

            Spacer(modifier = Modifier.height(8.dp))

            // Navigation Items
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                
                Text(
                    text = "মেনু",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )

                DrawerMenuItem(
                    label = "অফলাইন বইসমূহ",
                    icon = Icons.Outlined.DownloadDone,
                    onClick = { onOfflineBooksClick(); onCloseDrawer() }
                )
                
                DrawerMenuItem(
                    label = "আপডেট চেক করুন",
                    icon = Icons.Outlined.SystemUpdate,
                    onClick = { onCheckUpdateClick(); onCloseDrawer() }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                Text(
                    text = "কমিউনিটি ও সাহায্য",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )

                DrawerMenuItem(
                    label = "ফেসবুক গ্রুপ",
                    icon = Icons.Outlined.Groups,
                    onClick = { onFbGroupClick(); onCloseDrawer() }
                )

                DrawerMenuItem(
                    label = "অ্যাপটি শেয়ার করুন",
                    icon = Icons.Outlined.Share,
                    onClick = { onShareClick(); onCloseDrawer() }
                )

                DrawerMenuItem(
                    label = "রেটিং দিন",
                    icon = Icons.Outlined.StarOutline,
                    onClick = { onRateClick(); onCloseDrawer() }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                DrawerMenuItem(
                    label = "প্রাইভেসি পলিসি",
                    icon = Icons.Outlined.Policy,
                    onClick = { onPrivacyClick(); onCloseDrawer() }
                )
                
                DrawerMenuItem(
                    label = "আমাদের সম্পর্কে",
                    icon = Icons.Outlined.Info,
                    onClick = { onAboutClick(); onCloseDrawer() }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer (Version Info)
            DrawerFooter()
        }
    }
}

@Composable
private fun DrawerMenuItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
        },
        selected = false,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f),
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp), // Slightly rounded rectangle for modern apps
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .bounceClick { onClick() }
    )
}

@Composable
private fun DrawerFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "সংস্করণ: ১.০.০ • ২০২৬ শিক্ষাক্রম",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "সকল অধিকার জাতীয় শিক্ষাক্রম ও পাঠ্যপুস্তক বোর্ড দ্বারা সংরক্ষিত",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
