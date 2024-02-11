package com.viktorger.tinkofffintechandroid.network.model

import com.viktorger.tinkofffintechandroid.model.MovieShortcut

data class NetworkMovieResponse (
    val pagesCount: Int,
    val films: List<NetworkMovieShortcut>
)

data class NetworkMovieShortcut (
    val filmId: Int,
    val nameRu: String,
    val year: Int,
    val posterUrlPreview: String
)

fun NetworkMovieShortcut.asExternalModel(): MovieShortcut = MovieShortcut(
    id = filmId,
    title = nameRu,
    releaseDate = year,
    imageUrl = posterUrlPreview
)
