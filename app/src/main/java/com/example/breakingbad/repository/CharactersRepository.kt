package com.example.breakingbad.repository

import android.util.Log
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
//                emit(getCharactersCashed())
                emit(Resource.Loading())
                val response = charactersApi.getBBCharacters()
                val body = response.body()
                if (response.isSuccessful && body != null) {
//

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

    fun getCharactersFromRoom(): Flow<Resource<List<BBCharacter>>?> {
        return flow {
            try {
                emit(getCharactersCashed())

            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "unknown message"))
            }
        }.flowOn(Dispatchers.IO)

    }

    private fun getCharactersCashed(): Resource<List<BBCharacter>>? {
        return bbDao.getAllCharacters()?.let {
            Resource.Success(it)
        }
    }

    fun getCharacterById(id: Int): Flow<Resource<BBCharacter>?> {
        return flow {
            try {
                emit(Resource.Loading())
                emit(getCharacterByIdCashed(id))

            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun getCharacterByIdCashed(id: Int): Resource<BBCharacter>? {
        return bbDao.getCharacterById(id)?.let {
            Resource.Success(it)
        }
    }


}