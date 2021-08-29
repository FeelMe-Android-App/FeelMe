package com.feelme.feelmeapp.features.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.WatchingMovieItemBinding
import com.feelme.feelmeapp.features.home.model.Filmes

class FriendsMoviesAdapter(private val movies: MutableList<Filmes>, ): RecyclerView.Adapter<FriendsMoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = WatchingMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.count()

    class ViewHolder(private val binding: WatchingMovieItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Filmes) {
            with(binding) {
                rivMovie.setImageResource(movie.img)
            }
            this.itemView.setOnClickListener {

            }
        }
    }

}