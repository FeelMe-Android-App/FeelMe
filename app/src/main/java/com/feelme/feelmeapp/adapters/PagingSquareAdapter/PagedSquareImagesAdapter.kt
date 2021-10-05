package com.feelme.feelmeapp.adapters.PagingSquareAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.StreamItemBinding
import com.squareup.picasso.Picasso

class PagedSquareImagesAdapter(
    private val onClickListenerStreaming: (movie: PagedSquareImagesModel) -> Unit
): PagingDataAdapter<PagedSquareImagesModel, PagedSquareImagesAdapter.ViewHolder>(PagedSquareImagesModel.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StreamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListenerStreaming)
    }

    class ViewHolder(
        val binding: StreamItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: PagedSquareImagesModel?,
            onClickListenerStreaming: (movie: PagedSquareImagesModel) -> Unit
        ) {
            movie?.let {
                Picasso.get().load(movie.backdropPath).resize(500, 500).centerCrop().placeholder(
                    R.drawable.stream_not_image).into(binding.ivStreamerLogo)
                binding.ivStreamerLogo.setOnClickListener { onClickListenerStreaming(movie) }
            }
        }
    }
}