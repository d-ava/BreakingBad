package com.example.breakingbad.repository

import com.example.breakingbad.api.BBEpisodesApi
import com.example.breakingbad.db.EpisodesDao
import javax.inject.Inject

class EpisodesRepository @Inject constructor(
    private val episodesDao: EpisodesDao,
    private val episodesApi: BBEpisodesApi
) {
}