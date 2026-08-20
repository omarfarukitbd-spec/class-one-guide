package com.helptrickbd.class1.feature.drawing.domain.repository

import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem

interface TracingRepository {
    fun getItemsByCategory(category: TracingCategory): List<TracingItem>
    fun getAllCategories(): List<TracingCategory>
}
