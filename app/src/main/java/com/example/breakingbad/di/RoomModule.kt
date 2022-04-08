package com.example.breakingbad.di

import android.content.Context
import androidx.room.Room
import com.example.breakingbad.db.BBDatabase
import com.example.breakingbad.db.EpisodesDatabase
import com.example.breakingbad.db.QuotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideBBDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, BBDatabase::class.java, "bb_table")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideBBDao(db: BBDatabase) = db.bbDao()


    @Singleton
    @Provides
    fun provideQuotesDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, QuotesDatabase::class.java, "quotes_table")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideQuotesDao(db: QuotesDatabase) = db.quotesDao()

    @Singleton
    @Provides
    fun provideEpisodeDatabase(@ApplicationContext app:Context) =
        Room.databaseBuilder(app, EpisodesDatabase::class.java, "episodes_table")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesEpisodesDao(db:EpisodesDatabase) = db.episodesDao()

}