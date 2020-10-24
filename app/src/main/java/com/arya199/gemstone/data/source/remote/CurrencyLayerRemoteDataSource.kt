package com.arya199.gemstone.data.source.remote

import com.arya199.gemstone.api.CurrencyLayerApi
import com.arya199.gemstone.api.response.LiveResponse
import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.source.CurrencyLayerDataSource
import retrofit2.Retrofit
import javax.inject.Inject

class CurrencyLayerRemoteDataSource @Inject constructor(retrofit: Retrofit): CurrencyLayerDataSource {

    private val currencyLayerApi by lazy { retrofit.create(CurrencyLayerApi::class.java) }

    override suspend fun getLiveRate(): Result<LiveResponse> {
        return Result.Success(currencyLayerApi.live())
    }

    override suspend fun getCurrencyList(): Result<List<Currency>> {
        val listResponse = currencyLayerApi.list()
        val currencyToReturn = ArrayList<Currency>()
        if (listResponse.success) {
            val allKeys = listResponse.currencies.keys
            for (key in allKeys) {
                currencyToReturn.add(Currency(code = key, fullText = listResponse.currencies[key] ?: error("")))
            }
            return Result.Success(currencyToReturn)
        }
        else {
            return Result.Error(Exception("Failure on getting Exchange List from remote"))
        }
    }

    override suspend fun saveCurrency(currency: Currency) {
        TODO("Not yet implemented")
    }
}