package com.helptrickbd.class1.feature.games.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameOverSummary(
    score: Int,
    total: Int,
    onPlayAgain: () -> Unit,
    title: String = "🎉 অভিনন্দন! 🎉",
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = title,
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
