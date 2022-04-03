package com.example.breakingbad.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.breakingbad.App
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.model.BBEpisodes
import com.example.breakingbad.model.BBQuotes

@Database(entities = [BBEpisodes::class], version = 1, exportSchema = false)
@TypeConverters (Converters::class)
abstract class EpisodesDatabase : RoomDatabase() {

    abstract fun episodesDao(): EpisodesDao

    companion object {
        val dbEpisodes by lazy {
            Room.databaseBuilder(
                App.appContext!!,
                EpisodesDatabase::class.java, "episodes_table"

            ).fallbackToDestructiveMigration()
                .build()
        }
    }


}