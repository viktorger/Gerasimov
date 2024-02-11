package com.viktorger.tinkofffintechandroid.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.viktorger.tinkofffintechandroid.model.MovieShortcut

@Entity(tableName = "movie_favorite_shortcut")
data class MovieFavoriteShortcutEntity(
    @PrimaryKey @ColumnInfo(name = "movie_id") val movieId: Int,
    val title: String,
    @ColumnInfo("release_date") val releaseDate: String,
    @ColumnInfo("image_url") val imageUrl: String
)

fun MovieShortcut.asFavoriteEntity() = MovieFavoriteShortcutEntity(
    movieId = id,
    title = title,
    releaseDate = releaseDate,
    imageUrl = imageUrl
)

fun MovieFavoriteShortcutEntity.asExternalModel() = MovieShortcut(
    id = movieId,
    title = title,
    releaseDate = releaseDate,
    imageUrl = imageUrl,
    isFavorite = true
)