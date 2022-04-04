package com.example.breakingbad.db

import androidx.room.*
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.model.BBQuotes
import kotlinx.coroutines.flow.Flow

@Dao
interface BBDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacters(character: List<BBCharacter>)

    @Query("SELECT * FROM bb_table")
    fun getAllCharacters(): Flow<List<BBCharacter>>



    @Query("SELECT * FROM bb_table WHERE name = :character")
    fun getCharacterByName(character: String): Flow<BBCharacter>


}