package com.helptrickbd.class1.feature.learn_hub.data.datasource

import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem

/**
 * Aggregates all 39 Bengali Consonants (ব্যঞ্জনবর্ণ).
 * Modularized into small files to comply with Zero Large Files policy.
 */
object ConsonantsData {
    fun getConsonants(): List<PhonicsItem> {
        return ConsonantsPart1Data.items + ConsonantsPart2Data.items + ConsonantsPart3Data.items
    }
}
