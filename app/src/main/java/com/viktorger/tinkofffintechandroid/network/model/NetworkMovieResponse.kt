package com.viktorger.tinkofffintechandroid.network.model

import com.viktorger.tinkofffintechandroid.model.MovieShortcut

data class NetworkMovieResponse (
    val items: List<NetworkMovieShortcut>
)

data class NetworkMovieShortcut (
    val kinopoiskId: Int,
    val nameRu: String,
    val year: Int,
    val posterUrlPreview: String
)

fun NetworkMovieShortcut.asExternalModel(): MovieShortcut = MovieShortcut(
    id = kinopoiskId,
    title = nameRu,
    releaseDate = year,
    imageSource = posterUrlPreview
)
