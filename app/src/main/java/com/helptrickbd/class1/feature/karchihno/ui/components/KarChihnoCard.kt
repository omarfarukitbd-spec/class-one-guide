package com.helptrickbd.class1.feature.karchihno.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.helptrickbd.class1.feature.karchihno.domain.model.KarChihnoItem

@Composable
fun KarChihnoCard(
    item: KarChihnoItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFFFD54F).copy(alpha = 0.22f) else Color(0xFF1E2538),
        animationSpec = tween(250),
        label = "card_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFFFD54F) else Color(0xFF333E54),
        animationSpec = tween(250),
        label = "card_border"
    )

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = bgColor,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        modifier = modifier
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp)
        ) {
            Text(
                text = item.sign,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = if (isSelected) Color(0xFFFFD54F) else Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color(0xFFFFE082) else Color(0xFFCBD5E1)
            )
        }
    }
}
