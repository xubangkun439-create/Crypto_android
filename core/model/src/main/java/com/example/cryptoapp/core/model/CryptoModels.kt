package com.example.cryptoapp.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinPrice(
    val symbol: String,
    val name: String,
    val priceUsd: Double,
    val change24h: Double,
    val volume24h: Double,
    val timestamp: Long
)

@Serializable
data class NewsItem(
    val id: String,
    val title: String,
    val summary: String,
    val source: String,
    val publishedAt: String,
    val tags: List<String>
)

@Serializable
data class ForumPost(
    val id: String,
    val authorId: String,
    val authorName: String,
    val title: String,
    val content: String,
    @SerialName("created_at") val createdAt: String,
    val likeCount: Int,
    val replyCount: Int
)
