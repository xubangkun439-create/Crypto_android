package com.example.cryptoapp.core.network

import com.example.cryptoapp.core.model.CoinPrice
import com.example.cryptoapp.core.model.ForumPost
import com.example.cryptoapp.core.model.NewsItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoApiService {

    @GET("v1/market/prices")
    suspend fun getRealtimePrices(
        @Query("symbols") symbols: String,
        @Query("currency") currency: String = "USD"
    ): List<CoinPrice>

    @GET("v1/news/latest")
    suspend fun getLatestNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<NewsItem>

    @GET("v1/forum/posts")
    suspend fun getForumPosts(
        @Query("topic") topic: String? = null,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<ForumPost>

    @GET("v1/forum/posts/{id}")
    suspend fun getPostDetail(@Path("id") postId: String): ForumPost
}
