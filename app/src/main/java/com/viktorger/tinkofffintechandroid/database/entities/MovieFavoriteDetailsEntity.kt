package com.viktorger.tinkofffintechandroid.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.viktorger.tinkofffintechandroid.model.MovieDetails

@Entity(tableName = "movie_favorite_details")

data class MovieFavoriteDetailsEntity (
    @PrimaryKey @ColumnInfo(name = "movie_id") val id: Int,
    val title: String,
    val countries: String?,
    val description: String?,
    val genres: String?,
    @ColumnInfo("image_url") val imageUrl: String
)

fun MovieFavoriteDetailsEntity.asExternalModel(): MovieDetails = MovieDetails(
    title = title,
    countries = countries,
    description = description,
    genres = genres,
    imageUrl = imageUrl
)
