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
                val results = data?.results?.map { StreamDetails ->
                    StreamDetails.logoPath?.let { StreamDetails.logoPath = StreamDetails.logoPath.getFullImageUrl() }
                    StreamDetails
                }
                return ResponseApi.Success(results)
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }
}