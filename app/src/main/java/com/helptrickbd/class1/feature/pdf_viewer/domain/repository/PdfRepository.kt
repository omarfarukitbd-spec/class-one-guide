package com.helptrickbd.class1.feature.pdf_viewer.domain.repository

import com.helptrickbd.class1.feature.pdf_viewer.domain.model.DownloadState
import kotlinx.coroutines.flow.Flow

/**
 * Domain Repository interface for fetching and streaming PDF files.
 */
interface PdfRepository {
    fun getPdfFile(url: String): Flow<DownloadState>
}
