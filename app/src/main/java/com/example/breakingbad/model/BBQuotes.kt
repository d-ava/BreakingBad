package com.example.breakingbad.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BBQuotes(
    @Json(name = "quote_id")
    val quoteId: Int,
    @Json(name = "quote")
    val quote: String,
    @Json(name = "author")
    val author: String,
    @Json(name = "series")
    val series: String
)