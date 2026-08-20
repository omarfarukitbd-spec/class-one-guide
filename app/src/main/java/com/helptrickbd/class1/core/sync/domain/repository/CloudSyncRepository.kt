package com.helptrickbd.class1.core.sync.domain.repository

import com.helptrickbd.class1.core.sync.data.model.RemoteBookDto
import com.helptrickbd.class1.core.sync.data.model.RemoteChapterDto
import com.helptrickbd.class1.core.sync.data.model.RemoteClassMetadataDto

interface CloudSyncRepository {
    suspend fun getRemoteClassMetadata(classId: String): RemoteClassMetadataDto?
    suspend fun fetchRemoteBooksWithChapters(classId: String): List<Pair<RemoteBookDto, List<RemoteChapterDto>>>
    suspend fun getLocalLastSyncTimestamp(): Long
    suspend fun saveLocalLastSyncTimestamp(timestamp: Long)
}
