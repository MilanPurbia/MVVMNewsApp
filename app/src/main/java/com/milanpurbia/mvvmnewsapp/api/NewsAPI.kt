package com.milanpurbia.mvvmnewsapp.api

import com.milanpurbia.mvvmnewsapp.models.NewsResponse
import com.milanpurbia.mvvmnewsapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
     @Query("country")
     countryCode:String="us",
     @Query("page")
     pageNumber:Int=1,
     @Query("apikey")
     apiKey:String =API_KEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("country")
        searchQuery:String,
        @Query("page")
        pageNumber:Int=1,
        @Query("apikey")
        apiKey:String =API_KEY
    ):Response<NewsResponse>


}