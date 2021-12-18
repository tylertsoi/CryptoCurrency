package com.tyler.cryptocurrency.presentation.currencylist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyler.cryptocurrency.data.repositories.realm.RealmCurrencyListRepo
import com.tyler.cryptocurrency.domain.entities.CurrencyInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.tyler.cryptocurrency.presentation.currencylist.activity.DemoActivity
import kotlinx.coroutines.runBlocking

/**
 * ViewModel for getting CurrencyInfo data, used in [DemoActivity]
 */

@HiltViewModel
class DemoActivityViewModel @Inject constructor(
    private val repo: RealmCurrencyListRepo
) : ViewModel() {
    var currencyList: MutableLiveData<List<CurrencyInfo>> = MutableLiveData(listOf())

    private fun updateDisplayList(list: List<CurrencyInfo>) {
        currencyList.postValue(list)
    }

    suspend fun fetchCurrencyList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val list = repo.getCurrencyList()
                updateDisplayList(list)
            }
        }
    }
}