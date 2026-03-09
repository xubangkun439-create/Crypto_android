package com.example.cryptoapp.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinloreTickersResponse(
    val data: List<CoinloreTickerDto> = emptyList()
)

@Serializable
data class CoinloreTickerDto(
    val id: String,
    val symbol: String,
    val name: String,
    @SerialName("price_usd") val priceUsd: String,
    @SerialName("percent_change_24h") val percentChange24h: String,
    @SerialName("volume24") val volume24: Double? = null
)
