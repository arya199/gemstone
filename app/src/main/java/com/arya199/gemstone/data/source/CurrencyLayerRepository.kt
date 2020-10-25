package com.arya199.gemstone.data.source

import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Rate
import com.arya199.gemstone.data.Result

interface CurrencyLayerRepository {

    /**
     * Return pseudo-live List of Rate information. First, from the local source if
     * it is reasonably fresh, within thirty minutes of last update. Or remote source if
     * local store data is stale, or did not exist.
     */
    suspend fun getRates(forceUpdate: Boolean): Result<List<Rate>>

    /**
     * Mainly to be used during the population of Spinner down on currency selection, also used as
     * a reference to interpret country code. Always hit local, unless no data has yet to be
     * registered.
     */
    suspend fun getExchangeList(): Result<List<Currency>>
}