package com.helptrickbd.class1.feature.games.ui.hear_and_pick

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HearAndPickScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HearAndPickViewModel = hiltViewModel()
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
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরে যান"
                        )
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(end = 16.dp)
                    ) {
                        Text(
                            text = "শুনো ও সঠিক বর্ণ বেছে নাও",
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = Color(0xFFFFD54F),
                        trackColor = Color(0xFF1E2538)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "শব্দ শুনে সঠিক বর্ণে ট্যাপ করো:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Big Speaker Play Button
                    Surface(
                        shape = CircleShape,
                        color = if (uiState.isSpeaking) Color(0xFFFFD54F) else Color(0xFF252E42),
                        border = BorderStroke(2.dp, Color(0xFFFFD54F)),
                        modifier = Modifier
                            .size(96.dp)
                            .clickable(onClick = viewModel::playCurrentPrompt)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "শব্দ শুনুন",
                                tint = if (uiState.isSpeaking) Color.Black else Color(0xFFFFD54F),
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

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
                                    OptionButton(
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

@Composable
private fun OptionButton(
    letter: String,
    isSelected: Boolean,
    isCorrect: Boolean?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = when {
            isSelected && isCorrect == true -> Color(0xFF22C55E)
            isSelected && isCorrect == false -> Color(0xFFEF4444)
            else -> Color(0xFF1E2538)
        },
        animationSpec = tween(200),
        label = "opt_bg"
    )

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = bgColor,
        border = BorderStroke(2.dp, if (isSelected) Color.White else Color(0xFF333E54)),
        shadowElevation = 6.dp,
        modifier = modifier
            .height(90.dp)
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = letter,
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
        }
    }
}

@Composable
private fun GameOverSummary(
    score: Int,
    total: Int,
    onPlayAgain: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Text(
            text = "🎉 অভিনন্দন! 🎉",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFFFD54F)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "তুমি $total টি প্রশ্নের মধ্যে $score টি সঠিক উত্তর দিয়েছ!",
            fontSize = 16.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onPlayAgain,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD54F)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.height(52.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "আবার খেলুন", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
