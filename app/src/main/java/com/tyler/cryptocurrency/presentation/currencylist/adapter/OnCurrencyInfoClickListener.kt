package com.tyler.cryptocurrency.presentation.currencylist.adapter

import com.tyler.cryptocurrency.domain.entities.CurrencyInfo

interface OnCurrencyInfoClickListener {
    fun onItemClick(currencyInfo: CurrencyInfo)
}