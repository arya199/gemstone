package com.arya199.gemstone.data.source.local

import com.arya199.gemstone.api.response.LiveResponse
import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.source.CurrencyLayerDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyLayerLocalDataSource @Inject constructor(
    private val db: GemstoneDatabase
): CurrencyLayerDataSource {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getLiveRate(): Result<LiveResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrencyList(): Result<List<Currency>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(db.currencyDao().getCurrencies())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveCurrency(currency: Currency) = withContext(ioDispatcher) {
        db.currencyDao().insert(currency)
    }
}