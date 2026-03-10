package com.example.cryptoapp.data

import com.example.cryptoapp.core.model.CoinPrice
import com.example.cryptoapp.core.network.CryptoApiService
import com.example.cryptoapp.feature.market.MarketRepository

class MarketRepositoryImpl(
    private val apiService: CryptoApiService
) : MarketRepository {
    override suspend fun getRealtimePrices(symbols: List<String>): List<CoinPrice> {
        val symbolSet = symbols.map { it.uppercase() }.toSet()
        val response = apiService.getTickers(start = 0, limit = 100)

        return response.data
            .asSequence()
            .filter { it.symbol.uppercase() in symbolSet }
            .map { dto ->
                CoinPrice(
                    symbol = dto.symbol,
                    name = dto.name,
                    priceUsd = dto.priceUsd.toDoubleOrNull() ?: 0.0,
                    change24h = dto.percentChange24h.toDoubleOrNull() ?: 0.0,
                    volume24h = dto.volume24 ?: 0.0,
                    timestamp = System.currentTimeMillis()
                )
            }
            .toList()
    }
}
