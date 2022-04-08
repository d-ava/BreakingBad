package com.example.breakingbad.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.model.BBEpisodes
import com.example.breakingbad.model.BBQuotes
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEpisodes(episode: List<BBEpisodes>)

    @Query("SELECT * FROM episodes_table")
    fun getAllEpisodes(): List<BBEpisodes>?

//    @Query("SELECT * FROM quotes_table WHERE author = :author")
//    fun getAllQuotesFromAuthor(author: String): Flow<List<BBQuotes>>

    @Query("SELECT * FROM episodes_table WHERE episodeID = :episodeID")
    fun getEpisodeWithCharacters(episodeID: Int): Flow<List<BBEpisodes>>
}