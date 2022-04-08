package com.example.breakingbad.repository

import com.example.breakingbad.api.BBEpisodesApi
import com.example.breakingbad.db.EpisodesDao
import com.example.breakingbad.model.BBEpisodes
import com.example.breakingbad.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class EpisodesRepository @Inject constructor(
    private val episodesDao: EpisodesDao,
    private val episodesApi: BBEpisodesApi
) {

    fun getEpisodes(): Flow<Resource<List<BBEpisodes>>?> {
        return flow {
            try {
                emit(getEpisodesCashed())
                emit(Resource.Loading())
                val response = episodesApi.getEpisodes()
                val body = response.body()
                if (response.isSuccessful && body != null){
                    emit(Resource.Success(body))
                    episodesDao.insertEpisodes(body)
                }else {
                    emit(Resource.Error("unknown error"))
                }
            }catch (e:IOException){
                emit(Resource.Error(e.message?: "unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }


    private fun getEpisodesCashed(): Resource<List<BBEpisodes>>? {
        return episodesDao.getAllEpisodes()?.let {
            Resource.Success(it)
        }
    }




}