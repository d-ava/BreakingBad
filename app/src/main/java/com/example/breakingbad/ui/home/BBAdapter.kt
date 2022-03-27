package com.example.breakingbad.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.breakingbad.databinding.BbCharacterItemBinding
import com.example.breakingbad.extensions.glideExtension
import com.example.breakingbad.model.BBCharacter
import kotlin.coroutines.coroutineContext

class BBAdapter(
//    private val onItemClicked: ((bbCharacter: BBCharacter) -> Unit)
) : RecyclerView.Adapter<BBAdapter.BBCharacterViewHolder>() {

    private val list: MutableList<BBCharacter> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<BBCharacter>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BBAdapter.BBCharacterViewHolder {
        return BBCharacterViewHolder(
            BbCharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BBAdapter.BBCharacterViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class BBCharacterViewHolder(private val binding: BbCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: BBCharacter

        fun onBind() {
            item = list[adapterPosition]
            binding.tvName.text = item.name
//            binding.root.setOnClickListener {
//                onItemClicked(item)
//            }


//            binding.ivCharacter.glideExtension(item.img)

        }

    }

}