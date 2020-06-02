package com.milanpurbia.mvvmnewsapp.models

import com.milanpurbia.mvvmnewsapp.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)