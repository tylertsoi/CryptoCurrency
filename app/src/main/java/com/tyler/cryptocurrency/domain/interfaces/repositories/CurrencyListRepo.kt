package com.tyler.cryptocurrency.domain.interfaces.repositories

import com.tyler.cryptocurrency.domain.entities.CurrencyInfo

interface CurrencyListRepo {
    suspend fun getCurrencyList() : List<CurrencyInfo>
}