package com.viktorger.tinkofffintechandroid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.viktorger.tinkofffintechandroid.database.entities.MovieFavoriteDetailsEntity
import com.viktorger.tinkofffintechandroid.database.entities.MovieFavoriteShortcutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieFavoriteShortcutDao {
    @Query("SELECT * FROM movie_favorite_shortcut")
    suspend fun getAll(): List<MovieFavoriteShortcutEntity>

    @Query("SELECT movie_id FROM movie_favorite_shortcut")
    suspend fun getAllIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDetails(movieFavoriteShortcutEntity: MovieFavoriteShortcutEntity): Long

}