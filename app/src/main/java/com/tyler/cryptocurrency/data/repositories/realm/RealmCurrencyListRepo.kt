package com.tyler.cryptocurrency.data.repositories.realm

import com.tyler.cryptocurrency.data.repositories.realm.model.RealmCurrencyInfo
import com.tyler.cryptocurrency.data.repositories.realm.model.toDomainCurrencyInfo
import com.tyler.cryptocurrency.domain.entities.CurrencyInfo
import com.tyler.cryptocurrency.domain.interfaces.repositories.CurrencyListRepo
import io.realm.Realm
import javax.inject.Inject

class RealmCurrencyListRepo @Inject constructor() : CurrencyListRepo {
    lateinit var realm: Realm
    override suspend fun getCurrencyList(): List<CurrencyInfo> {
        realm = Realm.getDefaultInstance()
        val rmCurrencies = realm.where(RealmCurrencyInfo::class.java)
        return rmCurrencies.findAll().mapNotNull { it.toDomainCurrencyInfo() }
    }
}