package com.helptrickbd.class1.feature.learn_hub.ui.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.learn_hub.ui.quiz.components.*

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showQuitDialog by remember { mutableStateOf(false) }

    // 5-Tier Back Navigation System protocol
    BackHandler(enabled = true) {
        when (uiState) {
            is QuizUiState.Playing -> showQuitDialog = true
            is QuizUiState.GameOver -> viewModel.returnToModeSelection()
            else -> onBackClick()
        }
    }

    if (showQuitDialog) {
        QuizQuitDialog(
            onConfirmQuit = {
                showQuitDialog = false
                viewModel.returnToModeSelection()
            },
            onDismiss = { showQuitDialog = false }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            StandardTopBar(
                title = stringResource(R.string.quiz_screen_title),
                subtitle = when (val state = uiState) {
                    is QuizUiState.Playing -> stringResource(
                        R.string.quiz_question_counter,
                        state.currentIndex + 1,
                        state.totalQuestions
                    )
                    else -> stringResource(R.string.quiz_screen_subtitle)
                },
                navigationIcon = Icons.AutoMirrored.Rounded.ArrowBack,
                onNavigationClick = {
                    when (uiState) {
                        is QuizUiState.Playing -> showQuitDialog = true
                        is QuizUiState.GameOver -> viewModel.returnToModeSelection()
                        else -> onBackClick()
                    }
                },
                actions = {
                    if (uiState is QuizUiState.Playing) {
                        val playing = uiState as QuizUiState.Playing
                        IconButton(onClick = viewModel::toggleMute) {
                            Icon(
                                imageVector = if (playing.isMuted) Icons.Rounded.VolumeOff else Icons.Rounded.VolumeUp,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is QuizUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is QuizUiState.ModeSelection -> {
                    QuizModeSelector(
                        totalStars = state.totalStars,
                        onSelectMode = viewModel::selectMode
                    )
                }
                is QuizUiState.Playing -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Progress Bar & Stats
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LinearProgressIndicator(
                                progress = { state.progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                color = Color(0xFF10B981),
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            QuizQuestionCard(
                                question = state.currentQuestion,
                                comboStreak = state.comboStreak,
                                onReplayVoice = viewModel::replayQuestionVoice
                            )
                        }

                        // Answer Options
                        QuizOptionGrid(
                            options = state.currentQuestion.options,
                            selectedOption = state.selectedOption,
                            isAnswered = state.isAnswered,
                            targetLetter = state.currentQuestion.targetLetter,
                            onOptionSelected = viewModel::onOptionSelected,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    // Confetti Burst Overlay on Correct Answer
                    QuizConfettiCanvas(isActive = state.showConfetti)
                }
                is QuizUiState.GameOver -> {
                    QuizVictoryDialog(
                        result = state.result,
                        onPlayAgain = viewModel::playAgain,
                        onChooseMode = viewModel::returnToModeSelection
                    )
                }
            }
        }
    }
}
