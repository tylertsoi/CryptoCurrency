package com.tyler.cryptocurrency.data.repositories.realm.model

import com.tyler.cryptocurrency.domain.entities.CurrencyInfo
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class RealmCurrencyInfo : RealmObject {
    @PrimaryKey
    var id: String = ""

    @Required
    var name: String = ""

    @Required
    var symbol: String = ""

    constructor(id: String, name: String, symbol: String) {
        this.id = id
        this.name = name
        this.symbol = symbol
    }

    constructor()
}

fun RealmCurrencyInfo.toDomainCurrencyInfo(): CurrencyInfo {
    return CurrencyInfo(id, name, symbol)
}