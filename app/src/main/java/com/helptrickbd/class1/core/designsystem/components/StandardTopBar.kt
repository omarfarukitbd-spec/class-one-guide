package com.helptrickbd.class1.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.core.designsystem.theme.TopBarDark
import com.helptrickbd.class1.core.designsystem.modifiers.glassmorphism

/**
 * A globally configurable Top Bar that strictly handles native system UI insets.
 * Logic: Automatically determines content color based on background luminance.
 */
@Composable
fun StandardTopBar(
    title: String,
    subtitle: String? = null,
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppTheme.colors.topBarBackground,
    actions: @Composable RowScope.() -> Unit = {}
) {
    // Logic: Calculate content color (White for dark backgrounds, Black/Primary for light)
    val contentColor = if (backgroundColor.luminance() < 0.5f) Color.White else MaterialTheme.colorScheme.onSurface
    val isDark = backgroundColor == TopBarDark

    Surface(
        color = Color.Transparent,
        border = if (isDark) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)) else null,
        modifier = modifier
            .fillMaxWidth()
            .glassmorphism(
                color = backgroundColor.copy(alpha = 0.85f),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (navigationIcon != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = stringResource(R.string.desc_menu),
                        tint = contentColor
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(8.dp))
            }
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = title,
                    color = contentColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = contentColor.copy(alpha = 0.8f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        }
    }
}
