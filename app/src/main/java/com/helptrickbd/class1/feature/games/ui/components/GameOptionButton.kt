package com.helptrickbd.class1.feature.games.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameOptionButton(
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
