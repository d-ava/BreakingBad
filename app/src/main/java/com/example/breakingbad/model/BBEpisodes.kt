package com.example.breakingbad.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "episodes_table")
@JsonClass(generateAdapter = true)
data class BBEpisodes(
    @PrimaryKey(autoGenerate = false)
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
):Parcelable