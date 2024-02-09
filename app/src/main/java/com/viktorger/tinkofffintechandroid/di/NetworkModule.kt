package com.viktorger.tinkofffintechandroid.di

import com.viktorger.tinkofffintechandroid.network.KinopoiskService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class NetworkModule {

    @Provides
    fun provideKinopoiskService(): KinopoiskService {
        return Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KinopoiskService::class.java)
    }

}