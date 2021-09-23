package com.feelme.feelmeapp.features.movieDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.MovieStreamItemBinding
import com.feelme.feelmeapp.model.Flatrate
import com.squareup.picasso.Picasso

class MovieStreamingAdapter(
    private val streamingList: List<Flatrate>,
    private val onClickListenerStreaming: (streaming: Flatrate) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MovieStreamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(streamingList[position], onClickListenerStreaming)
    }

    override fun getItemCount() = streamingList.count()

    class ViewHolder(
        val binding: MovieStreamItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            streaming: Flatrate,
            onClickListenerStreaming: (streaming: Flatrate) -> Unit
        ) {
            Picasso.get().load(streaming.logoPath).placeholder(R.drawable.no_image).into(binding.ivStreamerLogo)
            binding.ivStreamerLogo.setOnClickListener { onClickListenerStreaming(streaming) }
        }
    }
}