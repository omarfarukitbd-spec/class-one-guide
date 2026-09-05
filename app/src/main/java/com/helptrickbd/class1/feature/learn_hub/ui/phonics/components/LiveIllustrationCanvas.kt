package com.helptrickbd.class1.feature.learn_hub.ui.phonics.components

import android.graphics.BitmapFactory
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.helptrickbd.class1.core.designsystem.modifiers.bounceClick
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem

/**
 * Cinematic Living Illustration Canvas.
 * Features:
 * 1. Glowing decorative border with vibrant gradient trim.
 * 2. Continuous breathing and gentle parallax float motion.
 * 3. Dynamic surge and light shimmer reflecting the sentence rhyme when playing.
 */
@Composable
fun LiveIllustrationCanvas(
    item: PhonicsItem,
    isPlaying: Boolean,
    onTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val shape = RoundedCornerShape(20.dp)

    val imageBitmap = remember(item.illustrationAssetPath) {
        try {
            item.illustrationAssetPath?.let { path ->
                context.assets.open(path).use { stream ->
                    BitmapFactory.decodeStream(stream)?.asImageBitmap()
                }
            }
        } catch (_: Exception) { null }
    }

    // Continuous Living Motion & Sentence Reflection Animations
    val infiniteTransition = rememberInfiniteTransition(label = "livingMotion")

    val breathingScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isPlaying) 1.08f else 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isPlaying) 900 else 2600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathingScale"
    )

    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatingOffset"
    )

    val borderPulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "borderPulse"
    )

    val borderBrush = Brush.linearGradient(
        colors = if (isPlaying) listOf(
            item.primaryColor,
            Color.White.copy(alpha = borderPulseAlpha),
            item.gradientColors.last(),
            Color.White.copy(alpha = borderPulseAlpha),
            item.primaryColor
        ) else listOf(
            item.primaryColor.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.3f),
            item.primaryColor.copy(alpha = 0.5f)
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .shadow(if (isPlaying) 12.dp else 4.dp, shape, spotColor = item.primaryColor)
            .clip(shape)
            .background(Color.White.copy(alpha = 0.05f))
            .border(if (isPlaying) 3.dp else 2.dp, borderBrush, shape)
            .bounceClick(scaleDown = 0.96f, onClick = onTap)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = breathingScale
                    scaleY = breathingScale
                    translationY = floatingOffset
                }
        ) {
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = item.word,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else if (item.vectorDrawableRes != null) {
                Icon(
                    painter = painterResource(item.vectorDrawableRes),
                    contentDescription = item.word,
                    tint = item.primaryColor,
                    modifier = Modifier.size(90.dp)
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(item.primaryColor.copy(alpha = 0.14f))
                ) {
                    Icon(item.icon, contentDescription = item.word, tint = item.primaryColor, modifier = Modifier.size(70.dp))
                }
            }
        }
    }
}
