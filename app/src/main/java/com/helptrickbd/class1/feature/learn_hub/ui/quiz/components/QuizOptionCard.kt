package com.helptrickbd.class1.feature.learn_hub.ui.quiz.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuizOptionCard(
    letter: String,
    isSelected: Boolean,
    isAnswered: Boolean,
    isCorrectTarget: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(isAnswered, isSelected) {
        if (isAnswered && isSelected && !isCorrectTarget) {
            shakeOffset.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 350
                    0f at 0
                    (-12f) at 50
                    12f at 100
                    (-8f) at 150
                    8f at 200
                    (-4f) at 250
                    0f at 300
                }
            )
        }
    }

    val cardColor by animateColorAsState(
        targetValue = when {
            isAnswered && isCorrectTarget -> Color(0xFF10B981)
            isAnswered && isSelected && !isCorrectTarget -> Color(0xFFEF4444)
            else -> MaterialTheme.colorScheme.surface
        },
        label = "cardColor"
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            isAnswered && (isCorrectTarget || isSelected) -> Color.White
            else -> MaterialTheme.colorScheme.onSurface
        },
        label = "contentColor"
    )

    Surface(
        onClick = onClick,
        enabled = !isAnswered,
        modifier = modifier
            .offset(x = shakeOffset.value.dp)
            .height(100.dp),
        shape = RoundedCornerShape(20.dp),
        color = cardColor,
        tonalElevation = if (isAnswered && (isCorrectTarget || isSelected)) 8.dp else 2.dp,
        border = BorderStroke(
            width = if (isAnswered && (isCorrectTarget || isSelected)) 2.dp else 1.dp,
            color = if (isAnswered && isCorrectTarget) Color(0xFF059669) else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = letter,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Black,
                    color = contentColor
                )
            )

            if (isAnswered && isCorrectTarget) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                )
            } else if (isAnswered && isSelected && !isCorrectTarget) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                )
            }
        }
    }
}

@Preview(name = "Quiz Option Card")
@Composable
private fun QuizOptionCardPreview() {
    com.helptrickbd.class1.core.designsystem.theme.AppTheme {
        QuizOptionCard(
            letter = "অ",
            isSelected = false,
            isAnswered = false,
            isCorrectTarget = true,
            onClick = {}
        )
    }
}
