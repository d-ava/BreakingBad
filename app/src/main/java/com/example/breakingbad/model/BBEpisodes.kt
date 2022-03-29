package com.example.breakingbad.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BBEpisodes(
    @Json(name = "episode_id")
    val episodeId: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "season")
    val season: String,
    @Json(name = "air_date")
    val airDate: String,
    @Json(name = "characters")
    val characters: List<String>,
    @Json(name = "episode")
    val episode: String,
    @Json(name = "series")
    val series: String
)