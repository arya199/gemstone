package com.arya199.gemstone.api

import com.arya199.gemstone.api.response.ListResponse
import com.arya199.gemstone.api.response.LiveResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * API listed here are only ones that were available for free user.
 */
interface CurrencyLayerApi {

    @GET("list")
    suspend fun list(): ListResponse

    @GET("live")
    suspend fun live(): LiveResponse

    @GET("historical")
    suspend fun historical()

    @GET("live")
    suspend fun specifyOutput(@Path("currencies") currencies: String)
}