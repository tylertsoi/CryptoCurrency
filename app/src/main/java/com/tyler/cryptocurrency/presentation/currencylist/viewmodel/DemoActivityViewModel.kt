package com.tyler.cryptocurrency.presentation.currencylist.viewmodel

import androidx.lifecycle.ViewModel
import com.tyler.cryptocurrency.data.repositories.realm.RealmCurrencyListRepo
import com.tyler.cryptocurrency.domain.entities.CurrencyInfo
import com.tyler.cryptocurrency.presentation.currencylist.activity.DemoActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for getting CurrencyInfo data, used in [DemoActivity]
 */

@HiltViewModel
class DemoActivityViewModel @Inject constructor(
    private val repo: RealmCurrencyListRepo
) : ViewModel() {
    fun fetchCurrencyList(): Flow<List<CurrencyInfo>> {
        return repo.currencyListFlow
    }
}