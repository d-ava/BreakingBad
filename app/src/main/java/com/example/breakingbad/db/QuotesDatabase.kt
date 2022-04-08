package com.example.breakingbad.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.breakingbad.App
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.model.BBQuotes

@Database(entities = [BBQuotes::class], version = 1, exportSchema = false)
//@TypeConverters (Converters::class)
abstract class QuotesDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDao
//
//    companion object {
//        val dbQuotes by lazy {
//            Room.databaseBuilder(
//                App.appContext!!,
//                QuotesDatabase::class.java, "quotes_table"
//
//            ).fallbackToDestructiveMigration()
//                .build()
//        }
//    }


}