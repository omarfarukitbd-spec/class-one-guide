package com.helptrickbd.class1.feature.home.domain.repository

import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.Subject
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Home feature data operations.
 */
interface HomeRepository {
    /**
     * Retrieves all subjects for the given curriculum.
     */
    fun getSubjects(curriculum: Curriculum): Flow<List<Subject>>
    
    /**
     * Retrieves the current reading progress for a user.
     */
    fun getResumeBook(): Flow<Book?>
}
