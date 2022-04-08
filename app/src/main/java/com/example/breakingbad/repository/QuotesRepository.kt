package com.example.breakingbad.repository

import com.example.breakingbad.api.BBQuotesApi
import com.example.breakingbad.db.QuotesDao
import com.example.breakingbad.model.BBQuotes

import com.example.breakingbad.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class QuotesRepository @Inject constructor(
    private val quotesApi: BBQuotesApi,
    private val quotesDao: QuotesDao
) {
    fun getQuotes(): Flow<Resource<List<BBQuotes>>?> {
        return flow {
            try {
                emit(getQuotesCashed())
                emit(Resource.Loading())
                val response = quotesApi.getQuotes()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    emit(Resource.Success(body))
                    quotesDao.insertQuotes(body)
                } else {
                    emit(Resource.Error("unknown error"))
                }
            }catch (e:IOException){
                emit(Resource.Error(e.message?:"unknown error"))
            }

        }.flowOn(Dispatchers.IO)

    }

    private fun getQuotesCashed(): Resource<List<BBQuotes>>? {
        return quotesDao.getAllQuotes()?.let {
            Resource.Success(it)
        }

    }


}