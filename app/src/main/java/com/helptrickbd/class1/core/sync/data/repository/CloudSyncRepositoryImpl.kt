package com.helptrickbd.class1.core.sync.data.repository

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.helptrickbd.class1.core.sync.data.model.RemoteBookDto
import com.helptrickbd.class1.core.sync.data.model.RemoteChapterDto
import com.helptrickbd.class1.core.sync.data.model.RemoteClassMetadataDto
import com.helptrickbd.class1.core.sync.data.model.RemoteResourceDto
import com.helptrickbd.class1.core.sync.domain.repository.CloudSyncRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import com.helptrickbd.class1.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudSyncRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firestore: FirebaseFirestore?,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CloudSyncRepository {

    private val prefs by lazy {
        context.getSharedPreferences("class1_sync_prefs", Context.MODE_PRIVATE)
    }

    private val _noticeFlow = MutableStateFlow<String?>(null)
    private val _minAppVersionFlow = MutableStateFlow(1)

    init {
        val cached = prefs.getString("cached_cloud_notice", null)
        _noticeFlow.value = cached
        _minAppVersionFlow.value = prefs.getInt("cached_min_app_version", 1)
    }

    override fun getCachedNoticeFlow(): Flow<String?> = _noticeFlow.asStateFlow()

    override suspend fun saveCachedNotice(notice: String?) = withContext(ioDispatcher) {
        prefs.edit().putString("cached_cloud_notice", notice).apply()
        _noticeFlow.value = notice
    }
    
    override fun getMinAppVersionFlow(): Flow<Int> = _minAppVersionFlow.asStateFlow()

    override suspend fun saveMinAppVersion(version: Int) = withContext(ioDispatcher) {
        prefs.edit().putInt("cached_min_app_version", version).apply()
        _minAppVersionFlow.value = version
    }

    override suspend fun getRemoteClassMetadata(classId: String): RemoteClassMetadataDto? = withContext(ioDispatcher) {
        val db = firestore ?: return@withContext null
        try {
            val doc = db.collection(com.helptrickbd.class1.core.config.AppConfig.CLOUD_ROOT_COLLECTION).document(classId).get().awaitTask()
            if (doc.exists()) {
                val metadataMap = doc.get("metadata") as? Map<*, *>
                RemoteClassMetadataDto(
                    lastUpdated = doc.getLong("lastUpdated") ?: 0L,
                    notice = metadataMap?.get("notice")?.toString(),
                    minAppVersion = (metadataMap?.get("minAppVersion") as? Long)?.toInt() ?: (metadataMap?.get("minAppVersion") as? Double)?.toInt() ?: 1
                )
            } else {
                null
            }
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun fetchRemoteBooksWithChapters(
        classId: String
    ): List<Pair<RemoteBookDto, List<RemoteChapterDto>>> = withContext(ioDispatcher) {
        val db = firestore ?: return@withContext emptyList()
        try {
            val doc = db.collection(com.helptrickbd.class1.core.config.AppConfig.CLOUD_ROOT_COLLECTION).document(classId).get().awaitTask()
            if (!doc.exists()) return@withContext emptyList()

            val resultList = mutableListOf<Pair<RemoteBookDto, List<RemoteChapterDto>>>()
            val booksRaw = doc.get("books") as? List<*> ?: emptyList<Any>()

            for (bookMapRaw in booksRaw) {
                val bookMap = bookMapRaw as? Map<*, *> ?: continue
                
                val bookDto = RemoteBookDto(
                    bookId = bookMap["bookId"]?.toString() ?: "",
                    title = bookMap["title"]?.toString() ?: "",
                    subtitle = bookMap["subtitle"]?.toString(),
                    pdfUrl = bookMap["pdfUrl"]?.toString() ?: "",
                    coverUrl = bookMap["coverUrl"]?.toString(),
                    curriculum = bookMap["curriculum"]?.toString() ?: "SCHOOL",
                    availableVersions = (bookMap["availableVersions"] as? List<*>)?.mapNotNull { it?.toString() } ?: listOf("BANGLA", "ENGLISH")
                )

                val chaptersRaw = bookMap["chapters"] as? List<*> ?: emptyList<Any>()
                val chaptersList = chaptersRaw.mapNotNull { chapMapRaw ->
                    val chapMap = chapMapRaw as? Map<*, *> ?: return@mapNotNull null
                    
                    val resourcesRaw = (chapMap["resources"] as? List<*>) ?: emptyList<Any>()
                    val resources = resourcesRaw.mapNotNull { resMapRaw ->
                        val resMap = resMapRaw as? Map<*, *> ?: return@mapNotNull null
                        RemoteResourceDto(
                            resourceId = resMap["resourceId"]?.toString() ?: "",
                            title = resMap["title"]?.toString() ?: "",
                            pdfUrl = resMap["pdfUrl"]?.toString() ?: "",
                            type = resMap["type"]?.toString() ?: "TEXTBOOK",
                            iconName = resMap["iconName"]?.toString()
                        )
                    }

                    RemoteChapterDto(
                        chapterId = chapMap["chapterId"]?.toString() ?: "",
                        unitNo = chapMap["unitNo"]?.toString() ?: "",
                        title = chapMap["title"]?.toString() ?: "",
                        version = chapMap["version"]?.toString() ?: "BANGLA",
                        orderIndex = (chapMap["orderIndex"] as? Long)?.toInt() ?: (chapMap["orderIndex"] as? Double)?.toInt() ?: 0,
                        resources = resources
                    )
                }

                if (bookDto.bookId.isNotEmpty()) {
                    resultList.add(Pair(bookDto, chaptersList))
                }
            }

            resultList
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getLocalLastSyncTimestamp(): Long = withContext(ioDispatcher) {
        prefs.getLong("last_sync_timestamp", 0L)
    }

    override suspend fun saveLocalLastSyncTimestamp(timestamp: Long) = withContext(ioDispatcher) {
        prefs.edit().putLong("last_sync_timestamp", timestamp).apply()
    }

    private suspend fun <T> Task<T>.awaitTask(): T =
        suspendCancellableCoroutine { cont ->
            addOnSuccessListener { result -> cont.resume(result, null) }
            addOnFailureListener { exception -> cont.resumeWith(Result.failure(exception)) }
        }
}
