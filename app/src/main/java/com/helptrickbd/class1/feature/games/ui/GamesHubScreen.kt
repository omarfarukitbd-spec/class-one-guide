package com.helptrickbd.class1.feature.games.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush as GradientBrush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GamesHubScreen(
    onNavigateToSlate: () -> Unit,
    onNavigateToKarChihno: () -> Unit,
    onNavigateToHearAndPick: () -> Unit,
    onNavigateToPictureMatch: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        item {
            HubHeader()
        }

        item {
            HubFeatureCard(
                title = "বর্ণমালা স্লেট ও হাত ঘোরানো",
                subtitle = "স্বরবর্ণ ও ব্যঞ্জনবর্ণ সুন্দর করে লেখার অনুশীলন",
                badgeText = "৫টি ক্যাটাগরি",
                icon = Icons.Default.Brush,
                gradient = listOf(Color(0xFF6366F1), Color(0xFF4338CA)),
                onClick = onNavigateToSlate
            )
        }

        item {
            HubFeatureCard(
                title = "কার-চিহ্ন ও শব্দ গঠন",
                subtitle = "আ-কার, ই-কার ও শুদ্ধ বানানের স্টুডিও",
                badgeText = "১০টি কার-চিহ্ন",
                icon = Icons.Default.AutoAwesome,
                gradient = listOf(Color(0xFF0D9488), Color(0xFF0F766E)),
                onClick = onNavigateToKarChihno
            )
        }

        item {
            HubFeatureCard(
                title = "শুনো ও সঠিক বর্ণ বেছে নাও",
                subtitle = "খাঁটি স্টুডিও ভয়েস শুনে সঠিক বর্ণে ট্যাপ করো",
                badgeText = "কুইজ গেম",
                icon = Icons.Default.Hearing,
                gradient = listOf(Color(0xFFD97706), Color(0xFFB45309)),
                onClick = onNavigateToHearAndPick
            )
        }

        item {
            HubFeatureCard(
                title = "ছবি দেখে বর্ণ মেলাও",
                subtitle = "মজার ছবি ও ছড়া দেখে শুরুর বর্ণ খুঁজে বের করো",
                badgeText = "ছবি কুইজ",
                icon = Icons.Default.ImageSearch,
                gradient = listOf(Color(0xFFE11D48), Color(0xFFBE123C)),
                onClick = onNavigateToPictureMatch
            )
        }
    }
}

@Composable
private fun HubHeader() {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "হাতেখড়ি শিক্ষা ও খেলাঘর",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "বর্ণমালা, কার-চিহ্ন ও মজার খেলার মাধ্যমে সহজ শিক্ষা",
            fontSize = 13.sp,
            color = Color(0xFF94A3B8)
        )
    }
}

@Composable
private fun HubFeatureCard(
    title: String,
    subtitle: String,
    badgeText: String,
    icon: ImageVector,
    gradient: List<Color>,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GradientBrush.horizontalGradient(gradient))
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier.size(54.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.Black.copy(alpha = 0.25f),
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = badgeText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD54F),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
