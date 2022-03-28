package com.example.breakingbad.ui.character

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.databinding.BbSeriesItemBinding

class SeriesAdapter : RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

    private val list: MutableList<String> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeriesAdapter.SeriesViewHolder {
        return SeriesViewHolder(
            BbSeriesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SeriesAdapter.SeriesViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SeriesViewHolder(private val binding: BbSeriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: String

        fun onBind() {
            model = list[adapterPosition]
            binding.tvSeasonNumber.text = model
        }


    }

}