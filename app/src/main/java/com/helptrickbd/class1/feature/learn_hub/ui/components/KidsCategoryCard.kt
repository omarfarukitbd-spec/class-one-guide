package com.helptrickbd.class1.feature.learn_hub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Spellcheck
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.designsystem.modifiers.bounceClick
import com.helptrickbd.class1.core.designsystem.modifiers.glassmorphism
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.feature.learn_hub.domain.model.KidsCategory

@Composable
fun KidsCategoryCard(
    category: KidsCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(22.dp)
    val baseGlassColor = if (MaterialTheme.colorScheme.background == Color.White) {
        category.primaryColor.copy(alpha = 0.08f)
    } else {
        category.primaryColor.copy(alpha = 0.15f)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .bounceClick(scaleDown = 0.94f, onClick = onClick)
            .glassmorphism(
                color = baseGlassColor,
                shape = cardShape,
                borderStroke = 1.dp
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Row: Icon Container + Badge
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Gradient Icon Box
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Brush.linearGradient(category.gradientColors))
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = stringResource(category.titleRes),
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            // Top-right Badge Pill
            Surface(
                shape = CircleShape,
                color = category.primaryColor.copy(alpha = 0.18f),
                contentColor = category.primaryColor
            ) {
                Text(
                    text = stringResource(category.badgeRes),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title and Description
        Column {
            Text(
                text = stringResource(category.titleRes),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(category.subtitleRes),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Subtle Action Indicator
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = null,
                tint = category.primaryColor.copy(alpha = 0.7f),
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Preview(name = "Kids Category Card - Light")
@Composable
private fun KidsCategoryCardPreview() {
    AppTheme(darkTheme = false) {
        KidsCategoryCard(
            category = KidsCategory(
                id = "vowels",
                titleRes = R.string.kids_cat_vowels_title,
                subtitleRes = R.string.kids_cat_vowels_desc,
                badgeRes = R.string.kids_cat_vowels_badge,
                icon = Icons.Rounded.Spellcheck,
                primaryColor = Color(0xFF10B981),
                gradientColors = listOf(Color(0xFF10B981), Color(0xFF059669)),
                route = Screen.Phonics("vowels")
            ),
            onClick = {}
        )
    }
}

@Preview(name = "Kids Category Card - Dark")
@Composable
private fun KidsCategoryCardDarkPreview() {
    AppTheme(darkTheme = true) {
        KidsCategoryCard(
            category = KidsCategory(
                id = "vowels",
                titleRes = R.string.kids_cat_vowels_title,
                subtitleRes = R.string.kids_cat_vowels_desc,
                badgeRes = R.string.kids_cat_vowels_badge,
                icon = Icons.Rounded.Spellcheck,
                primaryColor = Color(0xFF10B981),
                gradientColors = listOf(Color(0xFF10B981), Color(0xFF059669)),
                route = Screen.Phonics("vowels")
            ),
            onClick = {}
        )
    }
}
