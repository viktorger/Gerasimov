package com.viktorger.tinkofffintechandroid.di

import com.viktorger.tinkofffintechandroid.database.TFDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaosModule {

    @Singleton
    @Provides
    fun providesMovieFavoriteShortcutDao(
        database: TFDatabase
    ) = database.movieFavoriteShortcutDao()

    @Singleton
    @Provides
    fun providesMovieFavoriteDetailsDao(
        database: TFDatabase
    ) = database.movieFavoriteDetailsDao()

}