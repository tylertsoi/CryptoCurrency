package com.tyler.cryptocurrency.domain.entities

import io.realm.RealmModel

data class CurrencyInfo(
    val id: String,
    val name: String,
    val symbol: String
) : RealmModel