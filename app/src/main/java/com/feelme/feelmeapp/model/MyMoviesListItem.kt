package com.feelme.feelmeapp.model

import androidx.recyclerview.widget.DiffUtil

data class MyMoviesListItem(
    val __v: Int,
    val _id: String,
    var backdropPath: String,
    val createdAt: String,
    val id: String,
    val title: String,
    val uid: String,
    val updatedAt: String,
    val watched: Boolean
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<MyMoviesListItem> =
            object : DiffUtil.ItemCallback<MyMoviesListItem>() {
                override fun areItemsTheSame(
                    oldItem: MyMoviesListItem,
                    newItem: MyMoviesListItem
                ): Boolean {
                    return oldItem._id == newItem._id
                }

                override fun areContentsTheSame(
                    oldItem: MyMoviesListItem,
                    newItem: MyMoviesListItem
                ): Boolean {
                    return oldItem._id == newItem._id
                }
            }
    }
}