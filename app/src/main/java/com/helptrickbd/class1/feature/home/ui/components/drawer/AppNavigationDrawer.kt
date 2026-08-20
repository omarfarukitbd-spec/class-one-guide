package com.helptrickbd.class1.feature.home.ui.components.drawer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.core.settings.domain.model.StorageInfo
import com.helptrickbd.class1.core.settings.domain.model.ThemeMode
import com.helptrickbd.class1.feature.home.domain.model.Curriculum

@Composable
fun AppNavigationDrawer(
    storageInfo: StorageInfo,
    selectedTheme: ThemeMode,
    selectedCurriculum: Curriculum,
    onCurriculumSelected: (Curriculum) -> Unit,
    onThemeSelected: (ThemeMode) -> Unit,
    onClearCache: () -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier.width(320.dp),
        drawerContainerColor = MaterialTheme.colorScheme.background,
        drawerContentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            DrawerHeader()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Storage Manager
                StorageManagerCard(
                    storageInfo = storageInfo,
                    onClearCache = onClearCache
                )

                // 2. Theme Selector
                ThemeSelectorCard(
                    selectedTheme = selectedTheme,
                    onThemeSelected = onThemeSelected
                )

                // 3. Multi-Class Scalability Ecosystem Card
                OtherClassesCard()

                // 4. About & Version Footer (Consuming Navigation Bar Insets)
                DrawerFooter()
            }
        }
    }
}

@Composable
private fun OtherClassesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "অন্যান্য শ্রেণির গাইড",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "২য়, ৩য়, ৪র্থ ও ৫ম শ্রেণির গাইডবুক ও পাঠ্যবই অ্যাপ শীঘ্রই উন্মুক্ত করা হবে।",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DrawerFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .navigationBarsPadding(), // Consumes navigation bar insets
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
            fontSize = 10.sp
        )
    }
}
