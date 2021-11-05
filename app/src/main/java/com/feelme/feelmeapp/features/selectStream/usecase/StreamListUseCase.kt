package com.feelme.feelmeapp.features.selectStream.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.selectStream.repository.StreamListRepository
import com.feelme.feelmeapp.model.Stream
import com.feelme.feelmeapp.model.toStreamDb
import com.feelme.feelmeapp.modeldb.UserStreamList
import com.feelme.feelmeapp.modeldb.toStreamDetails
import com.feelme.feelmeapp.utils.ResponseApi
import okhttp3.internal.toImmutableList

class StreamListUseCase(private val streamListRepository: StreamListRepository) {
    suspend fun getStreamList(): ResponseApi {
        when(val responseApi = streamListRepository.getStreamList()) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? Stream
                val results = data?.results?.map { StreamDetails ->
                    StreamDetails.logoPath.let { StreamDetails.logoPath = StreamDetails.logoPath.getFullImageUrl() }
                    StreamDetails
                }

                results?.let { streamApi ->
                    val streamingDb: MutableList<com.feelme.feelmeapp.modeldb.Stream> = mutableListOf()

                    streamApi.forEach {
                        streamingDb.add(it.toStreamDb())
                    }

                    this.streamListRepository.saveStreamListDb(streamingDb)
                }

                return ResponseApi.Success(results)
            }
            is ResponseApi.Error -> {
                val streamDb = this.streamListRepository.getStreamListDb()
                if(streamDb.isNullOrEmpty()) return responseApi

                val streamList = streamDb.map {
                    it.toStreamDetails()
                }

                return ResponseApi.Success(streamList.toImmutableList())
            }
        }
    }

    suspend fun saveMyStreamListDb(streamList: List<UserStreamList>) {
        return streamListRepository.saveMyStreamListDb(streamList)
    }

    suspend fun getMyStreamListFromDb(): List<UserStreamList> {
        return streamListRepository.getMyStreamListFromDb()
    }
}