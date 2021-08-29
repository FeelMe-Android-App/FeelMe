package com.feelme.feelmeapp.model

data class StreamDetais(
    val display_priority: Int,
    var logo_path: String,
    val provider_id: Int,
    val provider_name: String,
    var selected: Boolean = false,
)