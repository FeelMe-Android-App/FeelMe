package com.feelme.feelmeapp.features.social.usecase

import android.net.Uri
import com.feelme.feelmeapp.adapters.PagingFriendsAdapter.PagedFriendsModel
import com.feelme.feelmeapp.model.FeelMeUserProfile

class SocialUseCase {
    fun setupSocialList(list: FeelMeUserProfile?): List<PagedFriendsModel> {
        val pagedSocialList: MutableList<PagedFriendsModel> = mutableListOf()
        list?.follow?.forEach{
            pagedSocialList.add(
                PagedFriendsModel(
                    userId = it.uid,
                    profilePicture = Uri.parse(it.photoUrl),
                    name = it.name
                )
            )
        }
        list?.follower?.forEach{
            pagedSocialList.add(
                PagedFriendsModel(
                    userId = it.uid,
                    profilePicture = Uri.parse(it.photoUrl),
                    name = it.name
                )
            )
        }
        return pagedSocialList
    }
}