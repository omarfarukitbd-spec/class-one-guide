package com.helptrickbd.class1.core.settings.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class StorageInfo(
    val cachedBytes: Long = 0L,
    val formattedSize: String = "০.০ MB",
    val cachedFilesCount: Int = 0,
    val cachedFiles: List<CachedFileInfo> = emptyList()
)

@Immutable
data class CachedFileInfo(
    val fileName: String,
    val displayName: String,
    val formattedSize: String,
    val bytes: Long
)
