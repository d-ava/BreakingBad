package com.example.breakingbad.api

import com.example.breakingbad.model.BBCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BBCharactersApi {

    @GET("characters")
    suspend fun getBBCharacters(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<List<BBCharacter>>


}