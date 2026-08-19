package com.helptrickbd.class1.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val iconUrl: String
)

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String,
    val subjectId: String,
    val name: String,
    val type: String,
    val pdfUrl: String
)
