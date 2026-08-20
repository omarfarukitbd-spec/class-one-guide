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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudSyncRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firestore: FirebaseFirestore?
) : CloudSyncRepository {

    private val prefs by lazy {
        context.getSharedPreferences("class1_sync_prefs", Context.MODE_PRIVATE)
    }

    override suspend fun getRemoteClassMetadata(classId: String): RemoteClassMetadataDto? = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext null
        try {
            val doc = db.collection("nctb_classes").document(classId).get().awaitTask()
            if (doc.exists()) {
                RemoteClassMetadataDto(
                    lastUpdated = doc.getLong("lastUpdated") ?: 0L,
                    notice = doc.getString("notice"),
                    minAppVersion = (doc.getLong("minAppVersion") ?: 1L).toInt()
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
    ): List<Pair<RemoteBookDto, List<RemoteChapterDto>>> = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext emptyList()
        try {
            val booksSnapshot = db.collection("nctb_classes")
                .document(classId)
                .collection("books")
                .get()
                .awaitTask()

            val resultList = mutableListOf<Pair<RemoteBookDto, List<RemoteChapterDto>>>()

            for (bookDoc in booksSnapshot.documents) {
                val bookDto = RemoteBookDto(
                    bookId = bookDoc.getString("bookId") ?: bookDoc.id,
                    title = bookDoc.getString("title") ?: "",
                    subtitle = bookDoc.getString("subtitle"),
                    pdfUrl = bookDoc.getString("pdfUrl") ?: "",
                    coverUrl = bookDoc.getString("coverUrl"),
                    curriculum = bookDoc.getString("curriculum") ?: "SCHOOL",
                    availableVersions = (bookDoc.get("availableVersions") as? List<*>)?.mapNotNull { it?.toString() } ?: listOf("BANGLA", "ENGLISH")
                )

                // Fetch chapters subcollection
                val chaptersSnapshot = bookDoc.reference.collection("chapters").get().awaitTask()
                val chaptersList = chaptersSnapshot.documents.map { chapDoc ->
                    val resourcesRaw = (chapDoc.get("resources") as? List<*>) ?: emptyList<Any>()
                    val resources = resourcesRaw.mapNotNull { resMap ->
                        (resMap as? Map<*, *>)?.let { map ->
                            RemoteResourceDto(
                                resourceId = map["resourceId"]?.toString() ?: "",
                                title = map["title"]?.toString() ?: "",
                                pdfUrl = map["pdfUrl"]?.toString() ?: "",
                                type = map["type"]?.toString() ?: "TEXTBOOK",
                                iconName = map["iconName"]?.toString()
                            )
                        }
                    }

                    RemoteChapterDto(
                        chapterId = chapDoc.getString("chapterId") ?: chapDoc.id,
                        unitNo = chapDoc.getString("unitNo") ?: "",
                        title = chapDoc.getString("title") ?: "",
                        version = chapDoc.getString("version") ?: "BANGLA",
                        orderIndex = (chapDoc.getLong("orderIndex") ?: 0L).toInt(),
                        resources = resources
                    )
                }

                resultList.add(Pair(bookDto, chaptersList))
            }

            resultList
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getLocalLastSyncTimestamp(): Long = withContext(Dispatchers.IO) {
        prefs.getLong("last_sync_timestamp", 0L)
    }

    override suspend fun saveLocalLastSyncTimestamp(timestamp: Long) = withContext(Dispatchers.IO) {
        prefs.edit().putLong("last_sync_timestamp", timestamp).apply()
    }

    private suspend fun <T> Task<T>.awaitTask(): T =
        suspendCancellableCoroutine { cont ->
            addOnSuccessListener { result -> cont.resume(result, null) }
            addOnFailureListener { exception -> cont.resumeWith(Result.failure(exception)) }
        }
}
