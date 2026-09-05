package com.helptrickbd.class1.feature.learn_hub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.feature.learn_hub.domain.provider.KidsCategoryProvider
import com.helptrickbd.class1.feature.learn_hub.ui.components.KidsCategoryCard
import com.helptrickbd.class1.feature.learn_hub.ui.components.KidsHeroBanner

/**
 * World-Class Interactive Kids Zone Hub.
 * Features unified top bar navigation and child-friendly responsive grid layout.
 */
@Composable
fun KidsZoneScreen(
    onNavigate: (Screen) -> Unit,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val categories = remember { KidsCategoryProvider.getCategories() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            StandardTopBar(
                title = stringResource(R.string.kids_zone_title),
                subtitle = stringResource(R.string.kids_zone_subtitle),
                navigationIcon = if (onBackClick != null) Icons.AutoMirrored.Rounded.ArrowBack else null,
                onNavigationClick = onBackClick ?: {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(top = 10.dp, bottom = 32.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Hero Banner spanning full width
                item(span = { GridItemSpan(maxLineSpan) }) {
                    KidsHeroBanner()
                }

                // Section Header
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = stringResource(R.string.kids_zone_subtitle),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }

                // Interactive Category Cards
                items(
                    items = categories,
                    key = { it.id }
                ) { category ->
                    KidsCategoryCard(
                        category = category,
                        onClick = { onNavigate(category.route) }
                    )
                }
            }
        }
    }
}

@Preview(name = "Kids Zone Screen - Light")
@Composable
private fun KidsZoneScreenPreview() {
    AppTheme(darkTheme = false) {
        KidsZoneScreen(onNavigate = {}, onBackClick = {})
    }
}

@Preview(name = "Kids Zone Screen - Dark")
@Composable
private fun KidsZoneScreenDarkPreview() {
    AppTheme(darkTheme = true) {
        KidsZoneScreen(onNavigate = {}, onBackClick = {})
    }
}
