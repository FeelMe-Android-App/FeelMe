package com.feelme.feelmeapp.model

data class MyMoviesList(
    val unwatched: List<MyMoviesListItem>?,
    val watched: List<MyMoviesListItem>?
)