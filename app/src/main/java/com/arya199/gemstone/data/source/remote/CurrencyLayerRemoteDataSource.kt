package com.arya199.gemstone.data.source.remote

import com.arya199.gemstone.api.CurrencyLayerApi
import com.arya199.gemstone.api.response.LiveResponse
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.source.CurrencyLayerDataSource
import retrofit2.Retrofit
import javax.inject.Inject

class CurrencyLayerRemoteDataSource @Inject constructor(retrofit: Retrofit): CurrencyLayerDataSource {

    private val currencyLayerApi by lazy { retrofit.create(CurrencyLayerApi::class.java) }

    override suspend fun getLiveRate(): Result<LiveResponse> {
        return Result.Success(currencyLayerApi.live())
    }
}