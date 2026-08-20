package com.helptrickbd.class1.feature.pdf_viewer.domain.model

import androidx.compose.runtime.Immutable
import java.io.File

/**
 * Immutable State for PDF Download & File Streaming.
 */
@Immutable
sealed interface DownloadState {
    @Immutable
    data class Progress(val progress: Float) : DownloadState

    @Immutable
    data class Success(val file: File) : DownloadState

    @Immutable
    data class Error(val message: String) : DownloadState
}
