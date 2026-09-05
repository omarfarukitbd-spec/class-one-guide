package com.helptrickbd.class1.feature.learn_hub.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceholderScreen(
    title: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            StandardTopBar(
                title = title,
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationClick = onBackClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$title Coming Soon!")
        }
    }
}
