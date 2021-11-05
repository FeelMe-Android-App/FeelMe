package com.feelme.feelmeapp.features.searchFriend.usecase

import android.net.Uri
import com.feelme.feelmeapp.adapters.PagingFriendsAdapter.PagedFriendsModel
import com.feelme.feelmeapp.model.feelmeapi.FeelMeUsersSearch

class SearchFriendUseCase {
    fun setupFriendsList(list: FeelMeUsersSearch?): List<PagedFriendsModel> {
        val pagedFriendsModel: MutableList<PagedFriendsModel> = mutableListOf()
        list?.users?.forEach {
            pagedFriendsModel.add(
                PagedFriendsModel(
                    userId = it.uid,
                    profilePicture = Uri.parse(it.photoUrl),
                    name = it.name
                )
            )
        }
        return pagedFriendsModel
    }
}