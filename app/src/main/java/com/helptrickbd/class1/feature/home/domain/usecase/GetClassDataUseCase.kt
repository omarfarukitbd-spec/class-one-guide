package com.helptrickbd.class1.feature.home.domain.usecase

import com.helptrickbd.class1.feature.home.domain.model.ClassData
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetClassDataUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(classId: String): Result<ClassData> {
        return repository.getClassData(classId)
    }
}
