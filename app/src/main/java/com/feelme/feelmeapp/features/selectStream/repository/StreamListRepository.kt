package com.feelme.feelmeapp.features.selectStream.repository

import android.content.Context
import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.database.FeelMeDatabase
import com.feelme.feelmeapp.utils.ResponseApi
import com.feelme.feelmeapp.modeldb.Stream

class StreamListRepository(private val context: Context) : BaseRepository() {
    suspend fun getStreamList(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getStreamList()
        }
    }

    suspend fun saveStreamListDb(streamList: MutableList<Stream>) {
        FeelMeDatabase.getDatabase(context).streamDao().insertAll(streamList)
    }

    suspend fun getStreamListDb(): List<Stream> {
        return FeelMeDatabase.getDatabase(context).streamDao().getAllStream()
    }
}