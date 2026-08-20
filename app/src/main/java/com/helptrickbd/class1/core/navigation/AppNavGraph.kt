package com.helptrickbd.class1.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.helptrickbd.class1.feature.home.presentation.HomeViewModel
import com.helptrickbd.class1.feature.home.ui.HomeScreen
import com.helptrickbd.class1.feature.pdf_viewer.ui.PdfViewerScreen
import com.helptrickbd.class1.feature.subject_detail.ui.SubjectDetailScreen
import com.helptrickbd.class1.feature.subject_detail.ui.SubjectDetailViewModel

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
            HomeScreen(
                viewModel = viewModel,
                onBookClick = { bookId, bookTitle ->
                    navController.navigate(
                        Screen.SubjectDetail(
                            subjectId = bookId,
                            subjectName = bookTitle
                        )
                    )
                }
            )
        }

        composable<Screen.SubjectDetail> {
            val detailViewModel: SubjectDetailViewModel = hiltViewModel()
            SubjectDetailScreen(
                viewModel = detailViewModel,
                onBackClick = { navController.popBackStack() },
                onResourceClick = { resource ->
                    navController.navigate(
                        Screen.PdfViewer(
                            resourceTitle = resource.title,
                            pdfUrl = resource.pdfUrl
                        )
                    )
                }
            )
        }

        composable<Screen.PdfViewer> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.PdfViewer>()
            PdfViewerScreen(
                title = args.resourceTitle,
                pdfUrl = args.pdfUrl,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
