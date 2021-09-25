package com.feelme.feelmeapp.features.selectStream.repository

import android.content.Context
import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.database.FeelMeDatabase
import com.feelme.feelmeapp.model.StreamDetails
import com.feelme.feelmeapp.model.toStreamDb
import com.feelme.feelmeapp.modeldb.Stream
import com.feelme.feelmeapp.utils.ResponseApi

class StreamListRepository(private val context: Context) : BaseRepository() {
    suspend fun getStreamList(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getStreamList()
        }
    }

    suspend fun saveStreamListDb(streamList: List<StreamDetails>?) {
        streamList?.let { streamApi ->
            val streamingDb: MutableList<com.feelme.feelmeapp.modeldb.Stream> = mutableListOf()

            streamApi.forEach {
                streamingDb.add(it.toStreamDb())
            }

            FeelMeDatabase.getDatabase(context).streamDao().insertAll(streamingDb)
        }
    }
}