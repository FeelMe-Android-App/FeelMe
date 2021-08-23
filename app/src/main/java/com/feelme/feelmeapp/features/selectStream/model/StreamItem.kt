package com.feelme.feelmeapp.features.selectStream.model

data class StreamItem (
    val id: Int,
    val image: Int,
    val name: String,
    var selected: Boolean = false,
)