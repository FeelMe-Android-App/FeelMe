package com.feelme.feelmeapp.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    val name: String
)