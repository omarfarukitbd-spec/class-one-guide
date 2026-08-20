package com.helptrickbd.class1.feature.drawing.domain.usecase

import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem
import com.helptrickbd.class1.feature.drawing.domain.repository.TracingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTracingItemsUseCase @Inject constructor(
    private val tracingRepository: TracingRepository
) {
    operator fun invoke(category: TracingCategory): List<TracingItem> {
        return tracingRepository.getItemsByCategory(category)
    }
}
