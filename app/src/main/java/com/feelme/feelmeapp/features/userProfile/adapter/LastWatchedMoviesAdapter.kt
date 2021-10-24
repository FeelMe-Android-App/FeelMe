package com.feelme.feelmeapp.features.userProfile.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.StreamItemBinding
import com.feelme.feelmeapp.model.MyMoviesListItem
import com.feelme.feelmeapp.model.feelmeapi.LastWatchedMovies
import com.squareup.picasso.Picasso

class LastWatchedMoviesAdapter(
    private val lastMovies: List<LastWatchedMovies>,
    private val onClickListener: (movie: LastWatchedMovies) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = StreamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(lastMovies[position], onClickListener)
    }

    override fun getItemCount() = lastMovies.count()

    class ViewHolder(
        val binding: StreamItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: LastWatchedMovies,
            onClickListener: (movie: LastWatchedMovies) -> Unit
        ) {
            Picasso.get().load(Uri.parse(movie.backdropPath)).resize(220, 340).centerCrop().placeholder(R.drawable.no_image).into(binding.ivStreamerLogo)
            binding.ivStreamerLogo.setOnClickListener {
                onClickListener(movie)
            }
        }
    }

}