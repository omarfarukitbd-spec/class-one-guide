package com.helptrickbd.class1.feature.home.domain.repository

import com.helptrickbd.class1.feature.home.domain.model.ClassData
import com.helptrickbd.class1.feature.home.domain.model.Subject

/**
 * Repository interface for Home-related data operations.
 * Follows Clean Architecture by residing in the Domain layer.
 */
interface HomeRepository {
    suspend fun getClassData(classId: String): Result<ClassData>
    suspend fun getSubjects(classId: String): Result<List<Subject>>
}
