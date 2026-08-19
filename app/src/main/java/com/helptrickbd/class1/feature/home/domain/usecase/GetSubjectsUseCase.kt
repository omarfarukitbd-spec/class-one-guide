package com.helptrickbd.class1.feature.home.domain.usecase

import com.helptrickbd.class1.feature.home.domain.model.Subject
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetSubjectsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(classId: String): Result<List<Subject>> {
        return repository.getSubjects(classId)
    }
}
