package com.feelme.feelmeapp.features.selectStream.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.StreamItemBinding
import com.feelme.feelmeapp.features.selectStream.model.StreamItem
import kotlin.coroutines.coroutineContext

class StreamAdapter(
    private val streamings: List<StreamItem>,
    private val onClickListener: (streaming: StreamItem) -> Unit
): RecyclerView.Adapter<StreamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamAdapter.ViewHolder {
        val binding = StreamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(streamings[position], onClickListener)
    }

    override fun getItemCount() = streamings.count()

    class ViewHolder(val binding: StreamItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            streaming: StreamItem,
            onClickListener: (streaming: StreamItem) -> Unit
        ) {
            if(streaming.selected) binding.ivStreamerLogo.alpha = 1F
            else binding.ivStreamerLogo.alpha = 0.3F

            binding.ivStreamerLogo.setImageResource(streaming.image)
            binding.ivStreamerLogo.setOnClickListener {
                streaming.selected = !streaming.selected
                if(streaming.selected) {
                    binding.ivStreamerLogo.alpha = 1F
                } else {
                    binding.ivStreamerLogo.alpha = 0.3F
                }
                onClickListener(streaming)
            }
        }
    }


}