package com.feelme.feelmeapp.adapters.PagingMovieGridAdapter

import androidx.recyclerview.widget.DiffUtil

data class PagedMovieGridModel(
    val movieId: Int,
    val backdropPath: String?,
    val title: String
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<PagedMovieGridModel> =
            object : DiffUtil.ItemCallback<PagedMovieGridModel>() {
                override fun areItemsTheSame(
                    oldItem: PagedMovieGridModel,
                    newItem: PagedMovieGridModel
                ): Boolean {
                    return oldItem.movieId == newItem.movieId
                }

                override fun areContentsTheSame(
                    oldItem: PagedMovieGridModel,
                    newItem: PagedMovieGridModel
                ): Boolean {
                    return oldItem.movieId == newItem.movieId
                }
            }
    }
}