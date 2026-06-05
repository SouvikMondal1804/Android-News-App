package com.example.demo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
        @GET("everything")
        fun searchNews(
            @Query("q") query: String,
            @Query("apiKey") apiKey: String
        ): Call<NewsResponse>
    }