package com.example.cryptoapp.core.network

import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiService {

    /**
     * Coinlore public API:
     * GET https://api.coinlore.net/api/tickers/?start=0&limit=100
     */
    @GET("api/tickers/")
    suspend fun getTickers(
        @Query("start") start: Int = 0,
        @Query("limit") limit: Int = 100
    ): CoinloreTickersResponse
}
