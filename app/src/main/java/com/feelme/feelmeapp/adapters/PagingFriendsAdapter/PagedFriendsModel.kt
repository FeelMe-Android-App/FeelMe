package com.feelme.feelmeapp.adapters.PagingFriendsAdapter

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class PagedFriendsModel(
    val userId: String,
    val profilePicture: Uri,
    val name: String
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<PagedFriendsModel> =
            object : DiffUtil.ItemCallback<PagedFriendsModel>() {
                override fun areContentsTheSame(
                    oldItem: PagedFriendsModel,
                    newItem: PagedFriendsModel
                ): Boolean {
                    return oldItem.userId == newItem.userId
                }

                override fun areItemsTheSame(
                    oldItem: PagedFriendsModel,
                    newItem: PagedFriendsModel
                ): Boolean {
                    return oldItem.userId == newItem.userId
                }
            }
    }
}