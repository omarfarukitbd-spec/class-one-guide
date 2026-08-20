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
            val viewModel: com.helptrickbd.class1.feature.home.presentation.HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onSubjectClick = { subjectId ->
                    navController.navigate(
                        Screen.SubjectDetail(
                            subjectId = subjectId,
                            subjectName = "" // Will be fetched in detail screen
                        )
                    )
                }
            )
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
