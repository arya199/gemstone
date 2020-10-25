package com.arya199.gemstone.data.source

import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Rate
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.succeeded
import com.arya199.gemstone.di.ConverterModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * Our source of truth is remote. But we would only hit remote at least once every thirty minutes.
 * Else, we hit local remote almost every single time.
 */
class DefaultCurrencyLayerRepository @Inject constructor(
    @ConverterModule.RealRemoteDataSourceAnnotation val remoteDataSource: CurrencyLayerDataSource,
    @ConverterModule.FakeRemoteDataSourceAnnotation val fakeRemoteDataSource: CurrencyLayerDataSource,
    @ConverterModule.LocalDataSourceAnnotation val localDataSource: CurrencyLayerDataSource
): CurrencyLayerRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getRates(): Result<List<Rate>> {
        return withContext(ioDispatcher) {
            val liveRateResult = fetchRateListFromLocaOrRemote()
            (liveRateResult as? Result.Success)?.let {
                if (it.data.isNotEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }
            return@withContext Result.Error(Exception("Illegal state"))
        }
    }

    override suspend fun getRate(amount: Double, source: String, to: String): Result<List<Rate>> {
        TODO("Not yet implemented")
    }

    override suspend fun getExchangeList(): Result<List<Currency>> {
        return withContext(ioDispatcher) {
            val listExchangeResult = fetchExchangeListFromLocalOrRemote()
            (listExchangeResult as? Result.Success)?.let {
                if (it.data.isNotEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }
            return@withContext Result.Error(Exception("Illegal state"))
        }
    }

    /**
     * Private functions to further shape up the data before sending them out, juggling between local and remote
     * among others.
     */

    private suspend fun fetchRateListFromLocaOrRemote(): Result<List<Rate>> {
        when (val localRateList = localDataSource.getLiveRate()) {
            is Result.Success -> {
                // TODO: Add a condition for stale data
                if (localRateList.data.isNotEmpty()) {
                    return localRateList
                }
            }
            is Result.Error -> Timber.w("Local data source fetch failed")
        }
        when (val remoteRateList = remoteDataSource.getLiveRate()) {
            is Result.Success -> {
                refreshRateLocalDataSource(remoteRateList.data)
                return remoteRateList
            }
            is Result.Error -> Timber.w("Remote data source fetch failed")
        }
        return Result.Error(Exception("Error fetching from remote and local"))
    }

    private suspend fun fetchExchangeListFromLocalOrRemote(): Result<List<Currency>> {
        // First, hit the local database, then if there are none, or the data is stale (let's make it
        // one day to define stale data for exchange list data, then hit remote
        when (val localExchangeResult = localDataSource.getCurrencyList()) {
            is Result.Success -> {
                // TODO: Add a condition for stale data as well
                if (localExchangeResult.data.isNotEmpty()) {
                    return localExchangeResult
                }
            }
            is Result.Error -> Timber.w("Local data source fetch failed")
        }
        when (val exchangeListResult = remoteDataSource.getCurrencyList()) {
            is Result.Success -> {
                refreshCurrencyLocalDataSource(exchangeListResult.data)
                return exchangeListResult
            }
            is Result.Error -> Timber.w("Remote data source fetch failed")
        }
        return Result.Error(Exception("Error fetching from remote and local"))
    }

    private suspend fun refreshCurrencyLocalDataSource(currencies: List<Currency>) {
        // TODO: Do we need the delete table before hand? Probably not.
        for (currency in currencies) {
            localDataSource.saveCurrency(currency)
        }
    }

    private suspend fun refreshRateLocalDataSource(rates: List<Rate>) {
        // TODO: Is UPDATE on conflict strategy enough?
        for (rate in rates) {
            localDataSource.saveRate(rate)
        }
    }

    private fun errorReport() {

    }

}