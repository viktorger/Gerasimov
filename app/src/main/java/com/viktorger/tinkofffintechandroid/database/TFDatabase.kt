package com.viktorger.tinkofffintechandroid.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.viktorger.tinkofffintechandroid.database.dao.MovieFavoriteDetailsDao
import com.viktorger.tinkofffintechandroid.database.dao.MovieFavoriteShortcutDao
import com.viktorger.tinkofffintechandroid.database.entities.MovieFavoriteDetailsEntity
import com.viktorger.tinkofffintechandroid.database.entities.MovieFavoriteShortcutEntity

@Database(
    entities = [
        MovieFavoriteShortcutEntity::class,
        MovieFavoriteDetailsEntity::class
   ],
    version = 1,
)
abstract class TFDatabase : RoomDatabase() {
    abstract fun movieFavoriteShortcutDao(): MovieFavoriteShortcutDao
    abstract fun movieFavoriteDetailsDao(): MovieFavoriteDetailsDao

    companion object {
        fun create(applicationContext: Context): TFDatabase = Room.databaseBuilder(
            applicationContext,
            TFDatabase::class.java, "tf-database"
        ).build()
    }
}