package com.helptrickbd.class1.feature.karchihno.domain.repository

import com.helptrickbd.class1.feature.karchihno.domain.model.KarChihnoItem

interface KarChihnoRepository {
    fun getKarChihnoItems(): List<KarChihnoItem>
}
