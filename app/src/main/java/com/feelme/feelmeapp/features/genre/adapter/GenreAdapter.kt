package com.feelme.feelmeapp.features.genre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.MovieSearchItemBinding
import com.feelme.feelmeapp.model.Result
import com.squareup.picasso.Picasso

class GenreAdapter(
    private val onClickListenerStreaming: (movie: Result) -> Unit
): PagingDataAdapter<Result, GenreAdapter.ViewHolder>(Result.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListenerStreaming)
    }

    class ViewHolder(
        val binding: MovieSearchItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: Result?,
            onClickListenerStreaming: (movie: Result) -> Unit
        ) {
            movie?.let {
                Picasso.get().load(movie.posterPath).placeholder(R.drawable.no_image).into(binding.ivMoviePoster)
                binding.tvMovieName.text = movie.title
                binding.ivMoviePoster.setOnClickListener { onClickListenerStreaming(movie) }
            }
        }
    }
}