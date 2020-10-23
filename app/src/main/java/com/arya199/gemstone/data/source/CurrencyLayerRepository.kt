package com.arya199.gemstone.data.source

import com.arya199.gemstone.data.Rate
import com.arya199.gemstone.data.Result

interface CurrencyLayerRepository {

    suspend fun getRates(): Result<List<Rate>>
}