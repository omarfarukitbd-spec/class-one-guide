package com.helptrickbd.class1.feature.learn_hub.ui.slate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateBoardTheme

@Composable
fun SlateBoardFrame(
    theme: SlateBoardTheme,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val frameShape = RoundedCornerShape(18.dp)
    val boardShape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(12.dp, frameShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        theme.frameColor.copy(alpha = 0.95f),
                        theme.frameColor,
                        theme.frameColor.copy(alpha = 0.85f)
                    )
                ),
                shape = frameShape
            )
            .border(
                width = 3.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.25f),
                        Color.Black.copy(alpha = 0.35f)
                    )
                ),
                shape = frameShape
            )
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(boardShape)
                .background(theme.boardColor),
            content = content
        )
    }
}
