package com.helptrickbd.class1.feature.karchihno.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.feature.karchihno.ui.components.KarChihnoBanner
import com.helptrickbd.class1.feature.karchihno.ui.components.KarChihnoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KarChihnoScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KarChihnoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF131824)),
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF131824),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরে যান"
                        )
                    }
                },
                title = {
                    Column {
                        Text(
                            text = "কার-চিহ্ন ও শব্দ গঠন",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "স্বরচিহ্ন চেনা ও সহজে বানান শেখা",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF131824))
        ) {
            KarChihnoBanner(
                item = uiState.selectedItem,
                isSpeaking = uiState.isSpeaking,
                isSpellingPlaying = uiState.isSpellingPlaying,
                onPlaySign = { uiState.selectedItem?.let { viewModel.playSignAudio(it) } },
                onPlaySpell = { uiState.selectedItem?.let { viewModel.playSpellAudio(it) } }
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(uiState.items, key = { it.id }) { item ->
                    KarChihnoCard(
                        item = item,
                        isSelected = uiState.selectedItem?.id == item.id,
                        onClick = { viewModel.selectItem(item) }
                    )
                }
            }
        }
    }
}
