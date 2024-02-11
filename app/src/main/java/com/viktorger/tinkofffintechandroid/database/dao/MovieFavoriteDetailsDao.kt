package com.viktorger.tinkofffintechandroid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.viktorger.tinkofffintechandroid.database.entities.MovieFavoriteDetailsEntity
import com.viktorger.tinkofffintechandroid.database.entities.MovieFavoriteShortcutEntity

@Dao
interface MovieFavoriteDetailsDao {

    @Query("SELECT * FROM movie_favorite_details WHERE movie_id=:id")
    suspend fun getDetailsById(id: Int): MovieFavoriteDetailsEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDetails(movieFavoriteDetailsEntity: MovieFavoriteDetailsEntity)

}