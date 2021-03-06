package com.arya199.gemstone.data.source

import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Rate
import com.arya199.gemstone.data.Result

/**
 * Interfacing to the underlying data source. Remote, Local, or Fake.
 * Function names should be explanatory.
 */
interface CurrencyLayerDataSource {

    suspend fun getLiveRate(): Result<List<Rate>>

    suspend fun getCurrencyList(): Result<List<Currency>>

    suspend fun saveCurrency(currency: Currency)

    suspend fun saveRate(rate: Rate)
}