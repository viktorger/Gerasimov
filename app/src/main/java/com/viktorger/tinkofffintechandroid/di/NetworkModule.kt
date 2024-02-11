package com.viktorger.tinkofffintechandroid.di

import com.viktorger.tinkofffintechandroid.network.retrofit.KinopoiskService
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun providesKinopoiskService(): KinopoiskService = KinopoiskService.create()

}