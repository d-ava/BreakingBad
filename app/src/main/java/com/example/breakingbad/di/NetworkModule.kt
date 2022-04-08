package com.example.breakingbad.di

import androidx.viewbinding.BuildConfig
import com.example.breakingbad.api.BBCharactersApi
import com.example.breakingbad.api.BBEpisodesApi
import com.example.breakingbad.api.BBQuotesApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://www.breakingbadapi.com/api/"

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: Moshi,
        client: OkHttpClient,

        ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun providesBBCharactersApi(retrofit: Retrofit.Builder):BBCharactersApi{
        return retrofit.build().create(BBCharactersApi::class.java)
    }

    @Singleton
    @Provides
    fun providesBBQuotesApi(retrofit: Retrofit.Builder):BBQuotesApi{
        return retrofit.build().create(BBQuotesApi::class.java)
    }


    @Singleton
    @Provides
    fun providesBBEpisodesApi (retrofit: Retrofit.Builder):BBEpisodesApi{
        return retrofit.build().create(BBEpisodesApi::class.java)
    }
}