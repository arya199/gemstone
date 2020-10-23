package com.arya199.gemstone.data.source

import com.arya199.gemstone.data.Rate
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.succeeded
import com.arya199.gemstone.di.ConverterModule
import javax.inject.Inject

class DefaultCurrencyLayerRepository @Inject constructor(
    @ConverterModule.RealRemoteDataSourceAnnotation val remoteDataSource: CurrencyLayerDataSource,
    @ConverterModule.FakeRemoteDataSourceAnnotation val fakeRemoteDataSource: CurrencyLayerDataSource
): CurrencyLayerRepository {

    override suspend fun getRates(): Result<List<Rate>> {
        val liveRateResult = fakeRemoteDataSource.getLiveRate()
        val rate = mutableListOf<Rate>()
        if (liveRateResult.succeeded) {
            when (liveRateResult) {
                is Result.Success -> {
                    val liveResponse = liveRateResult.data
                    if (liveResponse.success) {
                        val allKeys = liveResponse.quotes.keys
                        for (key in allKeys) {
                            val from = key.substring(0, 3)
                            val end = key.substring(3, key.length)
                            rate.add(Rate(from = from, to = end, rate = liveResponse.quotes[key]))
                        }
                    }
                    else {
                        errorReport()
                    }
                }
                is Result.Error -> {
                    Result.Error(liveRateResult.exception)
                }
            }
        }
        return Result.Success(rate)
    }

    private fun errorReport() {

    }
}