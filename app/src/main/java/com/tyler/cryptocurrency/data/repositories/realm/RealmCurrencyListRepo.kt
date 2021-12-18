package com.tyler.cryptocurrency.data.repositories.realm

import com.tyler.cryptocurrency.data.repositories.realm.model.RealmCurrencyInfo
import com.tyler.cryptocurrency.data.repositories.realm.model.toDomainCurrencyInfo
import com.tyler.cryptocurrency.domain.entities.CurrencyInfo
import com.tyler.cryptocurrency.domain.interfaces.repositories.CurrencyListRepo
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RealmCurrencyListRepo @Inject constructor() : CurrencyListRepo {
    lateinit var realm: Realm

    override val currencyListFlow: Flow<List<CurrencyInfo>> = flow {
        realm = Realm.getDefaultInstance()
        val records = realm.where(RealmCurrencyInfo::class.java)
            .findAll().mapNotNull { it.toDomainCurrencyInfo() }
        emit(records)
    }.flowOn(Dispatchers.IO)
}