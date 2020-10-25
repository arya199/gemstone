package com.arya199.gemstone.data.source.remote

import com.arya199.gemstone.api.CurrencyLayerApi
import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Rate
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.source.CurrencyLayerDataSource
import retrofit2.Retrofit
import javax.inject.Inject

class CurrencyLayerRemoteDataSource @Inject constructor(retrofit: Retrofit): CurrencyLayerDataSource {

    private val currencyLayerApi by lazy { retrofit.create(CurrencyLayerApi::class.java) }

    override suspend fun getLiveRate(): Result<List<Rate>> {
        val liveResponse = currencyLayerApi.live()
        val rateToReturn = ArrayList<Rate>()
        return if (liveResponse.success) {
            val allQuotePairs = liveResponse.quotes.keys
            for (key in allQuotePairs) {
                if (key.length != 6) continue
                val from = key.substring(0, 3)
                val end = key.substring(3, key.length)
                rateToReturn.add(Rate(from = from, to = end, rate = liveResponse.quotes[key]))
            }
            Result.Success(rateToReturn)
        } else {
            Result.Error(Exception("Failure on getting Quote List from remote: " +
                    "${liveResponse.error.code} : ${liveResponse.error.info}"))
        }
    }

    override suspend fun getCurrencyList(): Result<List<Currency>> {
        val listResponse = currencyLayerApi.list()
        val currencyToReturn = ArrayList<Currency>()
        return if (listResponse.success) {
            val allKeys = listResponse.currencies.keys
            for (key in allKeys) {
                currencyToReturn.add(Currency(code = key, fullText = listResponse.currencies[key] ?: error("")))
            }
            Result.Success(currencyToReturn)
        } else {
            Result.Error(Exception("Failure on getting Exchange List from remote"))
        }
    }

    override suspend fun saveCurrency(currency: Currency) {
        TODO("Not yet implemented")
    }

    override suspend fun saveRate(rate: Rate) {
        TODO("Not yet implemented")
    }
}