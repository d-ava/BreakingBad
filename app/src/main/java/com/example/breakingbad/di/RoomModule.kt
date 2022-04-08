package com.example.breakingbad.di

import android.content.Context
import androidx.room.Room
import com.example.breakingbad.db.BBDatabase
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
    fun provideBBDao(db: BBDatabase)=db.bbDao()

}