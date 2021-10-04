package com.feelme.feelmeapp.features.savedMovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.WatchingMovieItemBinding
import com.feelme.feelmeapp.model.MyMoviesListItem
import com.squareup.picasso.Picasso

class MyMoviesListAdapter(
    private val onClickListener: (movie: MyMoviesListItem) -> Unit
) : PagingDataAdapter<MyMoviesListItem, MyMoviesListAdapter.ViewHolder>(MyMoviesListItem.DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: MyMoviesListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyMoviesListAdapter.ViewHolder {
        val binding = WatchingMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(
        private val binding: WatchingMovieItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: MyMoviesListItem?,
            onClickListener: (movie: MyMoviesListItem) -> Unit
        ) = with(binding) {
            movie?.let {
                Picasso.get().load(movie.backdropPath).placeholder(R.drawable.no_image).into(rivMovie)
                rivMovie.setOnClickListener {
                    onClickListener(movie)
                }
            }
        }
    }
}