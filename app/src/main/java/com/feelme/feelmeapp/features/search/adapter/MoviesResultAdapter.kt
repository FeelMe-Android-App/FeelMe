package com.feelme.feelmeapp.features.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.MovieSearchItemBinding
import com.feelme.feelmeapp.model.Result
import com.squareup.picasso.Picasso

class MoviesResultAdapter(
        private val moviesList: List<Result>,
        private val onClickListenerStreaming: (movie: Result) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MovieSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(moviesList[position], onClickListenerStreaming)
    }

    override fun getItemCount() = moviesList.count()

    class ViewHolder(
            val binding: MovieSearchItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
                movie: Result,
                onClickListenerStreaming: (movie: Result) -> Unit
        ) {
            Picasso.get().load(movie.posterPath).placeholder(R.drawable.no_image).into(binding.ivMoviePoster)
            binding.tvMovieName.text = movie.title
            binding.ivMoviePoster.setOnClickListener { onClickListenerStreaming(movie) }
        }
    }
}