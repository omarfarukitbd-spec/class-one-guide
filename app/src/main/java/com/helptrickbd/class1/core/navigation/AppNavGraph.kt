package com.helptrickbd.class1.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.helptrickbd.class1.core.designsystem.theme.AppTheme
import com.helptrickbd.class1.feature.home.ui.HomeScreen
import com.helptrickbd.class1.feature.home.ui.HomeUiState
import com.helptrickbd.class1.feature.home.ui.HomeViewModel
import com.helptrickbd.class1.feature.home.ui.SubjectUiModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = modifier
    ) {
        composable<Screen.Home> {
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppTheme.brushes.deepSurface),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                is HomeUiState.Success -> {
                    val subjectUiModels = state.subjects.mapIndexed { index, subject ->
                        SubjectUiModel(
                            id = subject.subjectId,
                            title = subject.subjectName,
                            progress = when (index % 3) {
                                0 -> 0.75f
                                1 -> 0.45f
                                else -> 0.20f
                            }
                        )
                    }
                    HomeScreen(
                        userName = state.classData.className.ifEmpty { "Student" },
                        subjects = subjectUiModels,
                        onSubjectClick = { subject ->
                            navController.navigate(
                                Screen.SubjectDetail(
                                    subjectId = subject.id,
                                    subjectName = subject.title
                                )
                            )
                        }
                    )
                }
                is HomeUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppTheme.brushes.deepSurface),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        composable<Screen.SubjectDetail> {
            val detailViewModel: com.helptrickbd.class1.feature.subject_detail.ui.SubjectDetailViewModel = hiltViewModel()
            com.helptrickbd.class1.feature.subject_detail.ui.SubjectDetailScreen(
                viewModel = detailViewModel,
                onBackClick = { navController.popBackStack() },
                onBookClick = { book ->
                    navController.navigate(
                        Screen.PdfViewer(
                            bookId = book.bookId,
                            pdfUrl = book.pdfUrl
                        )
                    )
                }
            )
        }

        composable<Screen.PdfViewer> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.PdfViewer>()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.brushes.deepSurface)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PDF Viewer: Book ${args.bookId}\nURL: ${args.pdfUrl}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
