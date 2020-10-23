package com.arya199.gemstone.api

import com.arya199.gemstone.api.response.LiveResponse
import retrofit2.http.GET

interface CurrencyLayerApi {

    @GET("live")
    suspend fun live(): LiveResponse

    suspend fun convert()
}