package com.example.demo

data class NewsResponse(
    val articles: List<NewsArticle>
)

data class NewsArticle(
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val url: String
)