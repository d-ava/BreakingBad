package com.example.breakingbad.ui.season

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.R
import com.example.breakingbad.databinding.BbEpisodesItemBinding
import com.example.breakingbad.model.BBEpisodes

class EpisodesAdapter(private val onEpisodeClicked:((item:BBEpisodes)->Unit)) : RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>() {

    private var list:MutableList<BBEpisodes> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<BBEpisodes>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodesAdapter.EpisodesViewHolder {
        return EpisodesViewHolder(BbEpisodesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EpisodesAdapter.EpisodesViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class EpisodesViewHolder(private val binding: BbEpisodesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            private lateinit var model:BBEpisodes

            fun onBind(){
                model = list[adapterPosition]
                binding.root.setOnClickListener {
                    onEpisodeClicked(model)
                }
                binding.tvEpisodeName.text = model.title
                binding.tvEpisodeId.text = model.episodeId.toString()
                if ("S" in model.series){
                    binding.ivEpisodeLogo.setBackgroundResource(R.drawable.ic_better_cal_saul_logo)
                }else{
                    binding.ivEpisodeLogo.setBackgroundResource(R.drawable.ic_breaking_bad_logo)
                }
            }


    }

}