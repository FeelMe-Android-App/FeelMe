package com.feelme.feelmeapp.features.whatToWatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.MoodMovieItemBinding
import com.feelme.feelmeapp.model.Result
import com.squareup.picasso.Picasso

class MoviesMoodListAdapter(
    private val movies: List<Result>,
    private val onClickListener: (movie: Result) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MoodMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(movies[position])
    }

    override fun getItemCount() = movies.count()

    class ViewHolder(
        val binding: MoodMovieItemBinding,
        private val onClickListener: (movie: Result) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: Result
        ) {
            binding.tvMovieTitle.text = movie.title
            binding.tvMovieResume.text = movie.overview
            Picasso.get().load(movie.backdropPath).placeholder(R.drawable.no_image).into(binding.ivMoviePoster)
            binding.vgMovieItem.setOnClickListener {
                onClickListener(movie)
            }
        }
    }
}