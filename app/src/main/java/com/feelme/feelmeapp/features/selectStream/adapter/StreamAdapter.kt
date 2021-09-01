package com.feelme.feelmeapp.features.selectStream.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.StreamItemBinding
import com.feelme.feelmeapp.features.selectStream.usecase.StreamItem
import com.feelme.feelmeapp.model.StreamDetais
import com.squareup.picasso.Picasso

class StreamAdapter(
    private val streamings: List<StreamDetais>,
    private val onClickListener: (streaming: StreamDetais) -> Unit
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
            streaming: StreamDetais,
            onClickListener: (streaming: StreamDetais) -> Unit
        ) {
            if(streaming.selected) {
                binding.ivStreamerLogo.scaleX = 1F
                binding.ivStreamerLogo.scaleY = 1F
                binding.ivStreamerLogo.alpha = 1F
            }
            else {
                binding.ivStreamerLogo.scaleX = 0.9F
                binding.ivStreamerLogo.scaleY = 0.9F
                binding.ivStreamerLogo.alpha = 0.2F
            }

            binding.ivStreamerLogo.isVisible = true
            Picasso.get().load(streaming.logo_path).placeholder(R.drawable.skeleton).into(binding.ivStreamerLogo)
            binding.ivStreamerLogo.setOnClickListener {
                streaming.selected = !streaming.selected
                if(streaming.selected) {
                    binding.ivStreamerLogo.scaleX = 1F
                    binding.ivStreamerLogo.scaleY = 1F
                    binding.ivStreamerLogo.alpha = 1F
                } else {
                    binding.ivStreamerLogo.alpha = 0.3F
                    binding.ivStreamerLogo.scaleX = 0.9F
                    binding.ivStreamerLogo.scaleY = 0.9F
                }
                onClickListener(streaming)
            }
        }
    }
}