package com.viktorger.tinkofffintechandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.viktorger.tinkofffintechandroid.database.dao.MovieShortCutDao
import com.viktorger.tinkofffintechandroid.database.entities.MovieShortcutEntity

@Database(entities = [MovieShortcutEntity::class], version = 1)
abstract class TFDatabase : RoomDatabase() {
    abstract fun movieShortcutDao(): MovieShortCutDao
}