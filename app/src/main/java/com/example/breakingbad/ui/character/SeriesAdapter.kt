package com.example.breakingbad.ui.character

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.R
import com.example.breakingbad.databinding.BbSeriesItemBinding
import com.squareup.picasso.Picasso

class SeriesAdapter(
    private val onItemClicked: ((series: String) -> Unit)
) : RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

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
            binding.root.setOnClickListener {
                onItemClicked(model)
            }

            if ("b" in model) {
                binding.tvSeasonNumber.text = model
                binding.ivSeason.setBackgroundResource(R.drawable.ic_breaking_bad_logo)

            }
            else if ("s" in model){
                binding.tvSeasonNumber.text = model
                binding.ivSeason.setBackgroundResource(R.drawable.ic_better_cal_saul_logo)
            }
        }


    }

}