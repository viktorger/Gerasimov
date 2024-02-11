package com.viktorger.tinkofffintechandroid.di

import com.viktorger.tinkofffintechandroid.database.TFDatabase
import dagger.Module
import dagger.Provides

@Module
class DaosModule {

    @Provides
    fun providesMovieFavoriteShortcutDao(
        database: TFDatabase
    ) = database.movieFavoriteShortcutDao()

    @Provides
    fun providesMovieFavoriteDetailsDao(
        database: TFDatabase
    ) = database.movieFavoriteDetailsDao()

}