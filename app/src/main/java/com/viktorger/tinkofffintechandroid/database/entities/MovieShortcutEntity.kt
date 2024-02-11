package com.viktorger.tinkofffintechandroid.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_shortcut")
data class MovieShortcutEntity(
    @PrimaryKey val id: String,
    val title: String,
    @ColumnInfo("release_date") val releaseDate: Int,
    @ColumnInfo("image_url") val imageUrl: String
)
