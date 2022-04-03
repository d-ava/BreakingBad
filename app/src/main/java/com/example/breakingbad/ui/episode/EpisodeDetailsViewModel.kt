package com.example.breakingbad.ui.episode

import androidx.lifecycle.ViewModel
import com.example.breakingbad.db.EpisodesDao
import com.example.breakingbad.db.EpisodesDatabase

class EpisodeDetailsViewModel: ViewModel() {

    private var episodesDao: EpisodesDao = EpisodesDatabase.dbEpisodes.episodesDao()




}