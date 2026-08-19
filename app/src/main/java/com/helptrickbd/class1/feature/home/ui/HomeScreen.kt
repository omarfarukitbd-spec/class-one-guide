package com.helptrickbd.class1.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.feature.home.ui.components.GreetingSection
import com.helptrickbd.class1.feature.home.ui.components.SubjectCard

data class SubjectUiModel(
    val id: String,
    val title: String,
    val progress: Float
)

@Composable
fun HomeScreen(
    userName: String,
    subjects: List<SubjectUiModel>,
    onSubjectClick: (SubjectUiModel) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colors.deepSpace
    ) { innerPadding ->
        // Background Gradient Effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.brushes.deepSurface)
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    GreetingSection(userName = userName)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                items(subjects, key = { it.id }) { subject ->
                    SubjectCard(
                        title = subject.title,
                        progress = subject.progress,
                        onClick = { onSubjectClick(subject) }
                    )
                }
            }
        }
    }
}
