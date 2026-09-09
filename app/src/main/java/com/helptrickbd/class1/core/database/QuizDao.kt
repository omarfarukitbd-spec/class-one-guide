package com.helptrickbd.class1.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query("SELECT * FROM quiz_progress WHERE modeId = :modeId")
    fun getProgressByMode(modeId: String): Flow<QuizProgressEntity?>

    @Query("SELECT * FROM quiz_progress")
    fun getAllQuizProgress(): Flow<List<QuizProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: QuizProgressEntity)

    @Query("SELECT SUM(totalStars) FROM quiz_progress")
    fun getTotalStarsFlow(): Flow<Int?>
}
