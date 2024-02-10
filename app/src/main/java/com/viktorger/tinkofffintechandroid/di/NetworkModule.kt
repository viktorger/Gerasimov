package com.viktorger.tinkofffintechandroid.di

import com.viktorger.tinkofffintechandroid.network.KinopoiskNetworkDataSource
import com.viktorger.tinkofffintechandroid.network.retrofit.RetrofitKinopoiskNetwork
import dagger.Binds
import dagger.Module

@Module
abstract class NetworkModule {

    @Binds
    abstract fun provideKinopoiskNetwork(
        retrofitKinopoiskNetwork: RetrofitKinopoiskNetwork
    ): KinopoiskNetworkDataSource

}