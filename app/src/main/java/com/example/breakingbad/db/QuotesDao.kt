package com.example.breakingbad.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.model.BBQuotes
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuotes(quote: List<BBQuotes>)

    @Query("SELECT * FROM quotes_table")
    fun getAllQuotes(): List<BBQuotes>?

    @Query("SELECT * FROM quotes_table WHERE author = :author")
    fun getAllQuotesFromAuthor(author: String): Flow<List<BBQuotes>>


}