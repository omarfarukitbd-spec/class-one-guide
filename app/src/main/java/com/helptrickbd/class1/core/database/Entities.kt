package com.helptrickbd.class1.core.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.LanguageVersion
import com.helptrickbd.class1.feature.home.domain.model.Resource

@Entity(
    tableName = "books",
    indices = [Index(value = ["curriculum"]), Index(value = ["lastReadTimestamp"])]
)
data class BookEntity(
    @PrimaryKey val bookId: String,
    val title: String,
    val subtitle: String? = null,
    val pdfUrl: String = "",
    val coverUrl: String? = null,
    val curriculum: Curriculum = Curriculum.SCHOOL,
    val availableVersions: List<LanguageVersion> = listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH),
    val totalChapters: Int = 0,
    val isFavorite: Boolean = false,
    val progressPercent: Float = 0f,
    val lastReadPage: Int = 1,
    val lastReadTimestamp: Long = 0L
)

@Entity(
    tableName = "chapters",
    indices = [Index(value = ["bookId"])]
)
data class ChapterEntity(
    @PrimaryKey val chapterId: String,
    val bookId: String,
    val unitNo: String,
    val title: String,
    val version: LanguageVersion = LanguageVersion.BANGLA,
    val resources: List<Resource> = emptyList(),
    val orderIndex: Int = 0
)

@Entity(
    tableName = "bookmarks",
    indices = [Index(value = ["bookId"]), Index(value = ["bookId", "pageNumber"], unique = true)]
)
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val bookId: String,
    val pageNumber: Int,
    val title: String = "",
    val note: String? = null,
    val createdTimestamp: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "notifications",
    indices = [Index(value = ["timestamp"])]
)
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val message: String,
    val imageUrl: String? = null,
    val bookId: String? = null,
    val actionUrl: String? = null,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
