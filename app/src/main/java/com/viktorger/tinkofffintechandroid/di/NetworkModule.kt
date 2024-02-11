package com.viktorger.tinkofffintechandroid.di

import com.viktorger.tinkofffintechandroid.network.retrofit.KinopoiskService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesKinopoiskService(): KinopoiskService = KinopoiskService.create()

}