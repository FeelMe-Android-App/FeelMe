package com.feelme.feelmeapp.features.social.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.feelme.feelmeapp.adapters.PagingFriendsAdapter.PagedFriendsModel
import com.feelme.feelmeapp.adapters.PagingFriendsAdapter.PagedFriendsPagingSource
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.social.repository.SocialRepository
import com.feelme.feelmeapp.features.social.usecase.SocialUseCase
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SocialViewModel(
    private val socialUseCase: SocialUseCase,
    private val socialRepository: SocialRepository): BaseViewModel() {
    var sPagingData: Flow<PagingData<PagedFriendsModel>>? = null
    fun followUser(): Flow<PagingData<PagedFriendsModel>>{
        if(sPagingData != null) return sPagingData as Flow<PagingData<PagedFriendsModel>>
        else
            sPagingData = Pager(config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { PagedFriendsPagingSource { page ->
                    when(val responseApi = socialRepository.followUser(page)) {
                        is ResponseApi.Success -> {
                            val list = responseApi.data as? com.feelme.feelmeapp.model.FeelMeUserProfile
                            socialUseCase.setupSocialList(list)
                        }
                        is ResponseApi.Error -> {
                            listOf()
                        }
                    }
                }
                }
            ).flow.cachedIn(viewModelScope)
        return sPagingData as Flow<PagingData<PagedFriendsModel>>
    }
}