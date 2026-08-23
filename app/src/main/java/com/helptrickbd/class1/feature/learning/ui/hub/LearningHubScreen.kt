package com.helptrickbd.class1.feature.learning.ui.hub

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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar

@Composable
fun LearningHubScreen(
    onNavigateToSlate: () -> Unit,
    onNavigateToGames: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            StandardTopBar(
                title = "হাতেখড়ি শিক্ষা ও স্লেট",
                subtitle = "সহজ ও মজার ডিজিটাল হাতেখড়ি"
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = 24.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                LearningFeatureCard(
                    title = "বর্ণমালা ও সংখ্যা স্লেট",
                    subtitle = "অ-আ, ক-খ এবং ১-২ হাতে-কলমে লেখার অনুশীলন",
                    icon = Icons.Default.Brush,
                    gradient = listOf(Color(0xFF6366F1), Color(0xFF4338CA)),
                    onClick = onNavigateToSlate
                )
            }

            item {
                LearningFeatureCard(
                    title = "কার-চিহ্ন ও শব্দ গঠন",
                    subtitle = "সহজ উপায়ে কার-চিহ্ন চেনা ও শব্দ শেখার স্টুডিও",
                    icon = Icons.Default.AutoAwesome,
                    gradient = listOf(Color(0xFF0D9488), Color(0xFF0F766E)),
                    onClick = { /* TODO */ }
                )
            }

            item {
                LearningFeatureCard(
                    title = "মজার কুইজ ও খেলা",
                    subtitle = "শুনো ও সঠিক বর্ণটি খুঁজে বের করো",
                    icon = Icons.Default.Hearing,
                    gradient = listOf(Color(0xFFD97706), Color(0xFFB45309)),
                    onClick = onNavigateToGames
                )
            }
        }
    }
}

@Composable
private fun LearningFeatureCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    gradient: List<Color>,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(gradient))
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
