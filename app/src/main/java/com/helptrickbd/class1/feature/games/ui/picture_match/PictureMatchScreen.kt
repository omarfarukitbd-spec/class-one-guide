package com.helptrickbd.class1.feature.games.ui.picture_match

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.feature.drawing.ui.components.ConfettiCelebrationOverlay
import com.helptrickbd.class1.feature.games.ui.components.GameOptionButton
import com.helptrickbd.class1.feature.games.ui.components.GameOverSummary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureMatchScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PictureMatchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val current = uiState.currentQuestion

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
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরে যান")
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(end = 16.dp)
                    ) {
                        Text(
                            text = "শব্দ ও ছড়া দেখে বর্ণ মেলাও",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "স্কোর: ${uiState.score} ⭐",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD54F)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF131824))
        ) {
            if (uiState.isGameOver) {
                GameOverSummary(
                    score = uiState.score,
                    total = uiState.questions.size,
                    onPlayAgain = viewModel::startNewGame
                )
            } else if (current != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { (uiState.currentIndex + 1) / uiState.questions.size.toFloat() },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = Color(0xFFFFD54F),
                        trackColor = Color(0xFF1E2538)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Word Card
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFF1E2538),
                        border = BorderStroke(2.dp, Color(0xFF38BDF8)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { viewModel.playCurrentPrompt() })
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            IconButton(
                                onClick = { viewModel.playCurrentPrompt() },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = if (uiState.isSpeaking) Color(0xFFFFD54F) else Color(0xFF283349),
                                    contentColor = if (uiState.isSpeaking) Color.Black else Color.White
                                )
                            ) {
                                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "শব্দ শুনুন")
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = current.targetWord,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFFFD54F)
                                )
                                Text(
                                    text = "শব্দটি শুনে শুরুর বর্ণে ট্যাপ করো",
                                    fontSize = 13.sp,
                                    color = Color(0xFFCBD5E1)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "এই শব্দের শুরুর বর্ণটি কোনটি?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // 4 Options Grid (2x2)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (row in 0..1) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                for (col in 0..1) {
                                    val index = row * 2 + col
                                    val letter = current.options.getOrNull(index) ?: ""
                                    GameOptionButton(
                                        letter = letter,
                                        isSelected = uiState.selectedOptionIndex == index,
                                        isCorrect = if (uiState.selectedOptionIndex == index) uiState.isCorrect else null,
                                        onClick = { viewModel.selectOption(index) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            ConfettiCelebrationOverlay(
                celebrationState = uiState.celebrationState,
                onDismiss = {},
                onNext = {}
            )
        }
    }
}
