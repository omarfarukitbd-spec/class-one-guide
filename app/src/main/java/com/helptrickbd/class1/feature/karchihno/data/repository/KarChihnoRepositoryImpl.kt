package com.helptrickbd.class1.feature.karchihno.data.repository

import com.helptrickbd.class1.feature.karchihno.data.datasource.KarChihnoDataSource
import com.helptrickbd.class1.feature.karchihno.domain.model.KarChihnoItem
import com.helptrickbd.class1.feature.karchihno.domain.repository.KarChihnoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KarChihnoRepositoryImpl @Inject constructor() : KarChihnoRepository {
    override fun getKarChihnoItems(): List<KarChihnoItem> {
        return KarChihnoDataSource.items
    }
}
