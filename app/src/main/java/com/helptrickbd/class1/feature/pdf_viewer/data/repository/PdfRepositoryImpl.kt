package com.helptrickbd.class1.feature.pdf_viewer.data.repository

import com.helptrickbd.class1.feature.pdf_viewer.data.PdfDownloader
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.DownloadState
import com.helptrickbd.class1.feature.pdf_viewer.domain.repository.PdfRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PdfRepositoryImpl @Inject constructor(
    private val pdfDownloader: PdfDownloader
) : PdfRepository {

    override fun getPdfFile(url: String): Flow<DownloadState> {
        return pdfDownloader.downloadPdf(url)
    }
}
