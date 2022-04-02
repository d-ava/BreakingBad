package com.example.breakingbad.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.breakingbad.App
import com.example.breakingbad.model.BBCharacter

@Database(entities = [BBCharacter::class], version = 1, exportSchema = false)
@TypeConverters (Converters::class)
abstract class BBDatabase :RoomDatabase(){

    abstract fun bbDao(): BBDao

    companion object{
        val db by lazy {
            Room.databaseBuilder(
                App.appContext!!,
                BBDatabase::class.java, "bb_table"

            ).build()
        }
    }


}