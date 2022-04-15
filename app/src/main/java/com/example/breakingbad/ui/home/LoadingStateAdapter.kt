package com.example.breakingbad.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.databinding.ItemLoadStateBinding

class LoadingStateAdapter : LoadStateAdapter<LoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState)= LoadStateViewHolder(
        ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context))
    )



    inner class LoadStateViewHolder(private val binding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {

            binding.progressBarPaging.isVisible = loadState is LoadState.Loading



        }

    }
}