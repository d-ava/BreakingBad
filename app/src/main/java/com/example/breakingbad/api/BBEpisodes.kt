package com.example.breakingbad.api

import com.example.breakingbad.model.BBEpisodes
import retrofit2.Response
import retrofit2.http.GET

interface BBEpisodes {
    @GET("episodes")
    suspend fun getEpisodes(): Response<List<BBEpisodes>>
}