package com.helptrickbd.class1.core.database

import androidx.room.TypeConverter
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.LanguageVersion
import com.helptrickbd.class1.feature.home.domain.model.Resource
import com.helptrickbd.class1.feature.home.domain.model.ResourceType

/**
 * Type Converters for Room Database entities.
 */
class Converters {

    @TypeConverter
    fun fromLanguageVersionList(list: List<LanguageVersion>?): String {
        return list?.joinToString(",") { it.name } ?: ""
    }

    @TypeConverter
    fun toLanguageVersionList(data: String?): List<LanguageVersion> {
        if (data.isNullOrBlank()) return listOf(LanguageVersion.BANGLA)
        return data.split(",").mapNotNull { name ->
            runCatching { LanguageVersion.valueOf(name.trim()) }.getOrNull()
        }
    }

    @TypeConverter
    fun fromResourceList(list: List<Resource>?): String {
        if (list.isNullOrEmpty()) return ""
        return list.joinToString(separator = "###") { res ->
            "${res.resourceId}|||${res.title}|||${res.pdfUrl}|||${res.type.name}|||${res.iconName ?: ""}"
        }
    }

    @TypeConverter
    fun toResourceList(data: String?): List<Resource> {
        if (data.isNullOrBlank()) return emptyList()
        return data.split("###").mapNotNull { itemStr ->
            val parts = itemStr.split("|||")
            if (parts.size >= 4) {
                val type = runCatching { ResourceType.valueOf(parts[3]) }.getOrDefault(ResourceType.TEXTBOOK)
                Resource(
                    resourceId = parts[0],
                    title = parts[1],
                    pdfUrl = parts[2],
                    type = type,
                    iconName = parts.getOrNull(4)?.takeIf { it.isNotBlank() }
                )
            } else {
                null
            }
        }
    }

    @TypeConverter
    fun fromCurriculum(curriculum: Curriculum?): String {
        return curriculum?.name ?: Curriculum.SCHOOL.name
    }

    @TypeConverter
    fun toCurriculum(data: String?): Curriculum {
        return runCatching { Curriculum.valueOf(data ?: "") }.getOrDefault(Curriculum.SCHOOL)
    }
}
