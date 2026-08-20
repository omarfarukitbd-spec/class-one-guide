package com.helptrickbd.class1.feature.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Subject
import com.helptrickbd.class1.feature.home.presentation.HomeUiState
import com.helptrickbd.class1.feature.home.presentation.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onSubjectClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { DashboardTopBar() }
    ) { innerPadding ->
        when (val state = uiState) {
            is HomeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is HomeUiState.Success -> {
                HomeContent(
                    innerPadding = innerPadding,
                    subjects = state.subjects,
                    resumeBook = state.resumeBook,
                    onSubjectClick = onSubjectClick
                )
            }
            is HomeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun DashboardTopBar() {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "প্রথম শ্রেণির গাইড",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "২০২৭ শিক্ষাবর্ষের জন্য পরিমার্জিত",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    innerPadding: PaddingValues,
    subjects: List<Subject>,
    resumeBook: Book?,
    onSubjectClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        resumeBook?.let { 
            ResumeReadingCard(book = it)
            Spacer(modifier = Modifier.height(24.dp))
        }
        
        Text(
            text = "আপনার পাঠ্যবইসমূহ",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(subjects) { subject ->
                SubjectGridCard(
                    title = subject.subjectName,
                    progress = 0.5f,
                    onClick = { onSubjectClick(subject.subjectId) }
                )
            }
        }
    }
}

@Composable
fun ResumeReadingCard(book: Book) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(AppTheme.brushes.liquidMain)
            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp, 110.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Book, contentDescription = null, tint = Color.White)
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = "পড়া চালিয়ে যান",
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = book.title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "অধ্যায় - প্রগ্রেস ${ (book.progressPercent * 100).toInt() }%",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { book.progressPercent },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
            }
        }
        
        Surface(
            modifier = Modifier.align(Alignment.TopEnd),
            color = Color.White.copy(alpha = 0.15f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Whatshot,
                    contentDescription = null,
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "৫ দিন",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectGridCard(title: String, progress: Float, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                Icons.Default.Book,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )

            Column {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(CircleShape),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            }
        }
    }
}

@Composable
fun borderStroke(width: Dp, color: Color) = BorderStroke(width, color)
