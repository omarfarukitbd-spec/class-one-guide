package com.helptrickbd.class1.feature.karchihno.domain.usecase

import com.helptrickbd.class1.feature.karchihno.domain.model.KarChihnoItem
import com.helptrickbd.class1.feature.karchihno.domain.repository.KarChihnoRepository
import javax.inject.Inject

class GetKarChihnoItemsUseCase @Inject constructor(
    private val repository: KarChihnoRepository
) {
    operator fun invoke(): List<KarChihnoItem> {
        return repository.getKarChihnoItems()
    }
}
