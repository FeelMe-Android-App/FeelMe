package com.feelme.feelmeapp.adapters.PagingMovieComments

import androidx.recyclerview.widget.DiffUtil

data class PagedMovieCommentsModel(
    val backdropPath: String,
    val comment: String,
    val profilePhoto: String?,
    val uid: String,
    val movieId: Int,
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<PagedMovieCommentsModel> =
            object : DiffUtil.ItemCallback<PagedMovieCommentsModel>() {
                override fun areItemsTheSame(
                    oldItem: PagedMovieCommentsModel,
                    newItem: PagedMovieCommentsModel
                ): Boolean {
                    return oldItem.comment == newItem.comment
                }

                override fun areContentsTheSame(
                    oldItem: PagedMovieCommentsModel,
                    newItem: PagedMovieCommentsModel
                ): Boolean {
                    return oldItem.comment == newItem.comment
                }
            }
    }
}