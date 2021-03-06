package com.tyler.cryptocurrency.presentation.currencylist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyler.cryptocurrency.domain.entities.CurrencyInfo
import com.tyler.cryptocurrency.presentation.currencylist.fragment.CurrencyListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for list display of currency info, used in [CurrencyListFragment]
 */
class CurrencyListDisplayViewModel : ViewModel() {
    private var dataSet: List<CurrencyInfo> = listOf()
    private var sortingMode: SortingMode = SortingMode.None
    var showWelcomeMessage: MutableLiveData<Boolean> = MutableLiveData(true)

    private val _displayList = MutableStateFlow<List<CurrencyInfo>>(listOf())
    val displayList: StateFlow<List<CurrencyInfo>>
        get() = _displayList

    fun updateList(l: List<CurrencyInfo>) {
        // prevent issue of update list process runs when viewModel is destroyed
        viewModelScope.launch {
            dataSet = l
            _displayList.emit(getSortedList())
        }
    }

    fun sort() {
        // prevent issue of sorting process runs when viewModel is destroyed
        viewModelScope.launch {
            sortingMode = sortingMode.next()
            _displayList.emit(getSortedList())
        }
    }

    private suspend fun getSortedList(): List<CurrencyInfo> {
        val list: List<CurrencyInfo>
        // sort in the IO thread
        withContext(Dispatchers.IO) {
            list = when (sortingMode) {
                SortingMode.Descending -> dataSet.sortedByDescending { it.id }
                SortingMode.Ascending -> dataSet.sortedBy { it.id }
                else -> dataSet
            }
        }
        return list
    }

    enum class SortingMode(val id: Int) {
        None(0),
        Ascending(1),
        Descending(2);

        fun next(): SortingMode {
            return values().find { it.id == this.id + 1 } ?: None
        }
    }
}