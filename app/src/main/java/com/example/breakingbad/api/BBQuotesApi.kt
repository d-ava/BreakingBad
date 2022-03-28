package com.example.breakingbad.api

import com.example.breakingbad.model.BBQuotes
import retrofit2.Response
import retrofit2.http.GET

interface BBQuotesApi {

    @GET("quotes")
    suspend fun getQuotes():Response<List<BBQuotes>>

}