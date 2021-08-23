package com.feelme.feelmeapp.features.selectStream.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.StreamItemBinding
import com.feelme.feelmeapp.features.selectStream.model.StreamItem

class StreamAdapter(
    private val streamings: List<StreamItem>,
    private val onClickListener: (streaming: StreamItem) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = StreamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(streamings[position], onClickListener)
    }

    override fun getItemCount() = streamings.count()

    class ViewHolder(val binding: StreamItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            streaming: StreamItem,
            onClickListener: (streaming: StreamItem) -> Unit
        ) {
            binding.ivStreamerLogo.setImageResource(streaming.image)
            binding.ivStreamerLogo.setOnClickListener {
                if(binding.ivStreamerLogo.alpha != 1F) binding.ivStreamerLogo.alpha = 1F
                else binding.ivStreamerLogo.alpha = 0.3F
                onClickListener(streaming)
            }
        }
    }
}