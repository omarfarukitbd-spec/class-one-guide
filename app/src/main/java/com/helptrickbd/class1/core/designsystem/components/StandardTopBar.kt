package com.helptrickbd.class1.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.core.designsystem.theme.TopBarDark

/**
 * A globally configurable Top Bar that strictly handles native system UI insets.
 * Use this as the SSOT for all screen headers.
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
    val isDark = backgroundColor == TopBarDark

    Surface(
        color = backgroundColor,
        border = if (isDark) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)) else null,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding() // Content starts below status bar, but Surface covers it
                .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (navigationIcon != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = "Navigation",
                        tint = Color.White
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
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp,
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
