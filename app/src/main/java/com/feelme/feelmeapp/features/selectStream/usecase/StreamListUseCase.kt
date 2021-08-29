package com.feelme.feelmeapp.features.selectStream.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.selectStream.repository.StreamListRepository
import com.feelme.feelmeapp.model.Stream
import com.feelme.feelmeapp.utils.ResponseApi

class StreamListUseCase {
    private val streamList = StreamListRepository()

    suspend fun getStreamList(): ResponseApi {
        when(val responseApi = streamList.getStreamList()) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? Stream
                val results = data?.results?.map {
                    it.logo_path = it.logo_path.getFullImageUrl()
                    it
                }
                return ResponseApi.Success(results)
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }
}