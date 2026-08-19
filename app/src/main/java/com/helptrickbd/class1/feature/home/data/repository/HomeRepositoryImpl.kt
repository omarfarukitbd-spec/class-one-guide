package com.helptrickbd.class1.feature.home.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.helptrickbd.class1.feature.home.domain.model.ClassData
import com.helptrickbd.class1.feature.home.domain.model.Subject
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.delay

/**
 * Implementation of [HomeRepository] using Firebase Firestore.
 * Currently stubbed with mock data for UI testing.
 */
class HomeRepositoryImpl(
    private val firestore: FirebaseFirestore? = null
) : HomeRepository {

    override suspend fun getClassData(classId: String): Result<ClassData> {
        // Mocking network delay
        delay(500)
        return Result.success(
            ClassData(
                classId = classId,
                className = "Class 10",
                features = mapOf("pdf_viewer" to true, "quiz" to false)
            )
        )
    }

    override suspend fun getSubjects(classId: String): Result<List<Subject>> {
        delay(800)
        return Result.success(
            listOf(
                Subject("1", "Mathematics", "https://example.com/math.png"),
                Subject("2", "Physics", "https://example.com/physics.png"),
                Subject("3", "Chemistry", "https://example.com/chem.png")
            )
        )
    }
}
