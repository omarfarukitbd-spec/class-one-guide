package com.helptrickbd.class1.core.sync.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class RemoteClassMetadataDto(
    val lastUpdated: Long = 0L,
    val notice: String? = null,
    val minAppVersion: Int = 1
)

@Immutable
data class RemoteBookDto(
    val bookId: String = "",
    val title: String = "",
    val subtitle: String? = null,
    val pdfUrl: String = "",
    val coverUrl: String? = null,
    val curriculum: String = "SCHOOL",
    val availableVersions: List<String> = listOf("BANGLA", "ENGLISH")
)

@Immutable
data class RemoteChapterDto(
    val chapterId: String = "",
    val unitNo: String = "",
    val title: String = "",
    val version: String = "BANGLA",
    val orderIndex: Int = 0,
    val resources: List<RemoteResourceDto> = emptyList()
)

@Immutable
data class RemoteResourceDto(
    val resourceId: String = "",
    val title: String = "",
    val pdfUrl: String = "",
    val type: String = "TEXTBOOK",
    val iconName: String? = null
)
