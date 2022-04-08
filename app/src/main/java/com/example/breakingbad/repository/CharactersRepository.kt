package com.example.breakingbad.repository

import com.example.breakingbad.api.BBCharactersApi
import com.example.breakingbad.db.BBDao
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersApi: BBCharactersApi,
    private val bbDao: BBDao
) {


    fun getCharacters(): Flow<Resource<List<BBCharacter>>?> {
        return flow {
            try {
                emit(getCharactersCashed())
                emit(Resource.Loading())
                val response = charactersApi.getBBCharacters()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    emit(Resource.Success(body))
                    bbDao.insertCharacters(body)
                } else {
                    emit(Resource.Error("unknown error"))
                }
            } catch (e: IOException) {
                emit(Resource.Error(e.message ?: "unknown error"))
            }
        }.flowOn(Dispatchers.IO)


    }

    private fun getCharactersCashed(): Resource<List<BBCharacter>>? {
        return bbDao.getAllCharacters()?.let {
            Resource.Success(it)
        }


    }
}