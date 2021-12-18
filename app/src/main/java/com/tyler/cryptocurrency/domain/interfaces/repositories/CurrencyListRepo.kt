package com.tyler.cryptocurrency.domain.interfaces.repositories

import com.tyler.cryptocurrency.domain.entities.CurrencyInfo
import kotlinx.coroutines.flow.Flow

interface CurrencyListRepo {
    val currencyListFlow: Flow<List<CurrencyInfo>>
}