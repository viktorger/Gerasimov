package com.viktorger.tinkofffintechandroid.di

import com.viktorger.tinkofffintechandroid.data.DefaultMovieRepository
import com.viktorger.tinkofffintechandroid.data.MovieRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun provideMovieRepository(
        defaultMovieRepository: DefaultMovieRepository
    ): MovieRepository

}