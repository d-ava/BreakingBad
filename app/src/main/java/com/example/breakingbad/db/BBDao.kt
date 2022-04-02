package com.example.breakingbad.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.breakingbad.model.BBCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface BBDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacters(character: List<BBCharacter>)

    @Query("SELECT * FROM bb_table")
    fun getAllCharacters(): Flow<List<BBCharacter>>


}