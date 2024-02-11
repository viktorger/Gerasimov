package com.viktorger.tinkofffintechandroid.network.model

import com.viktorger.tinkofffintechandroid.model.MovieDetails

data class NetworkMovieDetailsResponse (
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String,
    val posterUrl: String,
    val description: String,
    val countries: List<CountryNetwork>,
    val genres: List<GenreNetwork>
)

data class CountryNetwork (
    val country: String
)

data class GenreNetwork (
    val genre: String
)


fun NetworkMovieDetailsResponse.asExternalModel() = MovieDetails(
    title = nameRu ?: nameEn ?: nameOriginal,
    description = description,
    countries = countries.joinToString(separator = ", ", transform = { it.country }),
    genres = genres.joinToString(separator = ", ", transform = { it.genre }),
    imageUrl = posterUrl
)