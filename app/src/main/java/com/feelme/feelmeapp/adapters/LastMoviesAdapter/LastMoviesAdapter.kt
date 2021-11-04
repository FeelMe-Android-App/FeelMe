package com.feelme.feelmeapp.adapters.LastMoviesAdapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.WatchingMovieItemBinding
import com.feelme.feelmeapp.model.feelmeapi.FriendMovieItem
import com.squareup.picasso.Picasso

class LastMoviesAdapter(private val movies: List<FriendMovieItem>, private val setOnClickListener: (movie: FriendMovieItem) -> Unit): RecyclerView.Adapter<LastMoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WatchingMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, setOnClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.count()

    class ViewHolder(private val binding: WatchingMovieItemBinding, private val setOnClickListener: (movie: FriendMovieItem) -> Unit): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: FriendMovieItem) {
            with(binding) {
                Picasso.get().load(Uri.parse(movie.backdropPath)).placeholder(R.drawable.no_image).into(rivMovie)
                Picasso.get().load(movie.uid.photoUrl).placeholder(R.drawable.no_image).into(rivUserProfile)
                vgMovieStatus.setOnClickListener { setOnClickListener(movie) }
            }
        }
    }
}