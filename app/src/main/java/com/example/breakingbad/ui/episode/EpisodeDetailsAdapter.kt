package com.example.breakingbad.ui.episode

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.databinding.BbEpisodeDetailsItemBinding
import com.example.breakingbad.databinding.BbEpisodesItemBinding
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.model.BBEpisodes
import com.squareup.picasso.Picasso

class EpisodeDetailsAdapter(/*private val onEpisodeClick:((episode:BBEpisodes)->Unit)*/) :
    RecyclerView.Adapter<EpisodeDetailsAdapter.EpisodeDetailsViewHolder>() {

    private val list: MutableList<BBCharacter> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<BBCharacter>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeDetailsViewHolder {
        return EpisodeDetailsViewHolder(
            BbEpisodeDetailsItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodeDetailsViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class EpisodeDetailsViewHolder(private val binding: BbEpisodeDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var model: BBCharacter

        fun onBind() {
            model = list[adapterPosition]

            binding.tvCharacterName.text = model.name
            Picasso.get().load(model.img).into(binding.ivCharacter)
        }


    }
}