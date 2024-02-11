package com.viktorger.tinkofffintechandroid.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.viktorger.tinkofffintechandroid.database.entities.MovieShortcutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieShortCutDao {
    @Query("SELECT * FROM movie_shortcut")
    fun getAll(): Flow<List<MovieShortcutEntity>>
}