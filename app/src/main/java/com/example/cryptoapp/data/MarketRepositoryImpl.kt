package com.example.cryptoapp.data

import com.example.cryptoapp.core.model.CoinPrice
import com.example.cryptoapp.core.network.CryptoApiService
import com.example.cryptoapp.feature.market.MarketRepository

class MarketRepositoryImpl(
    private val apiService: CryptoApiService
) : MarketRepository {
    override suspend fun getRealtimePrices(symbols: List<String>): List<CoinPrice> {
        val symbolParam = symbols.joinToString(separator = ",")
        return apiService.getRealtimePrices(symbols = symbolParam)
    }
}
