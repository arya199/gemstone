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
     * Given amount, source, and to, return list of all rates applicable. Restrictions are
     * similar to getRates function, and added caveat as well that since we're in a basic plan
     * all conversions but USD will go through in-device computation first.
     */
    suspend fun getRate(amount: Double, source: String, to: String): Result<List<Rate>>

    /**
     * Mainly to be used during the population of Spinner down on currency selection, also used as
     * a reference to interpret country code. Always hit local, unless no data has yet to be
     * registered.
     */
    suspend fun getExchangeList(): Result<List<Currency>>
}