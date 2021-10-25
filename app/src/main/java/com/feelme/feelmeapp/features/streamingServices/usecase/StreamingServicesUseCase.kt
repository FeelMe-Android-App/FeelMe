package com.feelme.feelmeapp.features.streamingServices.usecase

import com.feelme.feelmeapp.features.streamingServices.repository.StreamingServicesRepository
import com.feelme.feelmeapp.modeldb.UserStreamListWithStream

class StreamingServicesUseCase(private val streamingServicesRepository: StreamingServicesRepository) {
    suspend fun getMyStreamingServices(): List<UserStreamListWithStream> {
        return streamingServicesRepository.getMyStreamingServices()
    }
}