package com.example.breakingbad.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.databinding.BbCharacterItemBinding
import com.example.breakingbad.model.BBCharacter
import com.squareup.picasso.Picasso

class BBPagingAdapter(
    private val onItemClicked: ((bbCharacter: BBCharacter) -> Unit)
) : PagingDataAdapter<BBCharacter, BBPagingAdapter.BBCharacterViewHolder>(DiffCallBack()) {

//    private var list: MutableList<BBCharacter> = mutableListOf()
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(list: List<BBCharacter>) {
//        this.list.clear()
//        this.list.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun filteredList(flist: MutableList<BBCharacter>) {
//        list = flist
//        notifyDataSetChanged()
//    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BBPagingAdapter.BBCharacterViewHolder {
        return BBCharacterViewHolder(
            BbCharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BBPagingAdapter.BBCharacterViewHolder, position: Int) {
        holder.onBind(getItem(position)!!)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    inner class BBCharacterViewHolder(private val binding: BbCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        private lateinit var item: BBCharacter

        fun onBind(item:BBCharacter) {
//            item = list[adapterPosition]
            binding.tvName.text = item.name
            Picasso.get().load(item.img).into(binding.ivCharacter)


            binding.root.setOnClickListener {
                onItemClicked(item)
            }


        }

    }

    class DiffCallBack : DiffUtil.ItemCallback<BBCharacter>() {
        override fun areItemsTheSame(oldItem: BBCharacter, newItem: BBCharacter) =
            oldItem.charId == newItem.charId

        override fun areContentsTheSame(oldItem: BBCharacter, newItem: BBCharacter) =
            oldItem == newItem
    }


}