package com.example.breakingbad.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity (tableName = "quotes_table")
@JsonClass(generateAdapter = true)
data class BBQuotes(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "quote_id")
    val quoteId: Int,
    @Json(name = "quote")
    val quote: String,
    @Json(name = "author")
    val author: String,
//    @Json(name = "series")
//    val series: String
)