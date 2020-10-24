package com.arya199.gemstone.data.source

import com.arya199.gemstone.api.response.LiveResponse
import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Result

interface CurrencyLayerDataSource {

    suspend fun getLiveRate(): Result<LiveResponse>

    suspend fun getCurrencyList(): Result<List<Currency>>

    suspend fun saveCurrency(currency: Currency)
}