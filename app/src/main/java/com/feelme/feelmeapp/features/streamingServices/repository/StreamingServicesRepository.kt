package com.feelme.feelmeapp.features.streamingServices.repository

import android.content.Context
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.database.FeelMeDatabase
import com.feelme.feelmeapp.modeldb.UserStreamListWithStream

class StreamingServicesRepository(private val context: Context): BaseRepository() {
    suspend fun getMyStreamingServices(): List<UserStreamListWithStream> {
        return FeelMeDatabase.getDatabase(context).userStream().getUserStream()
    }
}