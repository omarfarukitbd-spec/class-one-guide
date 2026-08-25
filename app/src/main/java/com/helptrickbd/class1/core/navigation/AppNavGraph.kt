package com.helptrickbd.class1.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.helptrickbd.class1.feature.main.ui.MainScreen
import com.helptrickbd.class1.feature.pdf_viewer.ui.PdfViewerScreen
import com.helptrickbd.class1.feature.pdf_viewer.ui.PdfViewerViewModel
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
            MainScreen(
                onBookClick = { bookId, bookTitle ->
                    navController.navigate(
                        Screen.SubjectDetail(
                            subjectId = bookId,
                            subjectName = bookTitle
                        )
                    )
                },
                onResumeClick = { resumeBook ->
                    if (resumeBook.pdfUrl.isNotBlank()) {
                        navController.navigate(
                            Screen.PdfViewer(
                                resourceTitle = resumeBook.title,
                                pdfUrl = resumeBook.pdfUrl,
                                bookId = resumeBook.bookId,
                                initialPage = resumeBook.lastReadPage
                            )
                        )
                    } else {
                        navController.navigate(
                            Screen.SubjectDetail(
                                subjectId = resumeBook.bookId,
                                subjectName = resumeBook.title
                            )
                        )
                    }
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
                            pdfUrl = resource.pdfUrl,
                            bookId = detailViewModel.bookId
                        )
                    )
                }
            )
        }

        composable<Screen.PdfViewer> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.PdfViewer>()
            val pdfViewModel: PdfViewerViewModel = hiltViewModel()
            PdfViewerScreen(
                title = args.resourceTitle,
                pdfUrl = args.pdfUrl,
                bookId = args.bookId,
                initialPage = args.initialPage,
                viewModel = pdfViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
