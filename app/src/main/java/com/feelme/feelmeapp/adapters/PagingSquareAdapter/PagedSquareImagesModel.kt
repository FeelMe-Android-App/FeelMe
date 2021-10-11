package com.feelme.feelmeapp.adapters.PagingSquareAdapter

import androidx.recyclerview.widget.DiffUtil

data class PagedSquareImagesModel(
    val movieId: Int,
    val backdropPath: String?
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<PagedSquareImagesModel> =
            object : DiffUtil.ItemCallback<PagedSquareImagesModel>() {
                override fun areItemsTheSame(
                    oldItem: PagedSquareImagesModel,
                    newItem: PagedSquareImagesModel
                ): Boolean {
                    return oldItem.movieId == newItem.movieId
                }

                override fun areContentsTheSame(
                    oldItem: PagedSquareImagesModel,
                    newItem: PagedSquareImagesModel
                ): Boolean {
                    return oldItem.movieId == newItem.movieId
                }
            }
    }
}