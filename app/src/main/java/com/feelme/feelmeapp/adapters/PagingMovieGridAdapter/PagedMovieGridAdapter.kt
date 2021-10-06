package com.feelme.feelmeapp.adapters.PagingMovieGridAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.MovieSearchItemBinding
import com.squareup.picasso.Picasso

class PagedMovieGridAdapter(
    private val onClickListenerMovie: (movie: PagedMovieGridModel) -> Unit
) : PagingDataAdapter<PagedMovieGridModel, PagedMovieGridAdapter.ViewHolder>(PagedMovieGridModel.DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListenerMovie)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = MovieSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(
        val binding: MovieSearchItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: PagedMovieGridModel?,
            onClickListenerMovie: (movie: PagedMovieGridModel) -> Unit
        ) {
            movie?.let {
                Picasso.get().load(movie.backdropPath).placeholder(
                    R.drawable.no_image).into(binding.ivMoviePoster)
                binding.tvMovieName.text = movie.title
                binding.vgMovieItem.setOnClickListener {
                    onClickListenerMovie(movie)
                }
            }
        }
    }
}