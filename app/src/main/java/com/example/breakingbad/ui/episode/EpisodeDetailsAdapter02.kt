package com.example.breakingbad.ui.episode

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.R
import com.example.breakingbad.databinding.BbCharacterItemBinding
import com.example.breakingbad.databinding.BbEpisodeDetailsItemBinding
import com.example.breakingbad.model.BBCharacter
import com.squareup.picasso.Picasso

class EpisodeDetailsAdapter02(
    private val onItemClicked: ((bbCharacter: BBCharacter) -> Unit)
) : RecyclerView.Adapter<EpisodeDetailsAdapter02.EpisodeDetailsViewHolder>() {

    private val list: MutableList<BBCharacter> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<BBCharacter>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeDetailsAdapter02.EpisodeDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return EpisodeDetailsViewHolder(
            BbEpisodeDetailsItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: EpisodeDetailsAdapter02.EpisodeDetailsViewHolder,
        position: Int
    ) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class EpisodeDetailsViewHolder(private val binding: BbEpisodeDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: BBCharacter

        fun onBind() {
            item = list[adapterPosition]
            binding.ivCharacter.setOnClickListener {
                onItemClicked(item)
            }
            binding.tvCharacterName.text = item.name
            binding.tvActorName.text = item.portrayed
            binding.tvCharacterNickname.text = item.nickname
            Picasso.get().load(item.img).into(binding.ivCharacter)


        }

    }

}