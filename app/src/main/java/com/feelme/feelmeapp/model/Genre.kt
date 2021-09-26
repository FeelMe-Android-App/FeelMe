package com.feelme.feelmeapp.model

data class Genre(
    val id: Int,
    val name: String
)

fun Genre.toGenreDb(): com.feelme.feelmeapp.modeldb.Genre {
    return com.feelme.feelmeapp.modeldb.Genre(
        id = this.id,
        name = this.name
    )
}