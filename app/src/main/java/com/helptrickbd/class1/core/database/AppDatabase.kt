package com.helptrickbd.class1.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SubjectEntity::class, BookEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun bookDao(): BookDao
}
