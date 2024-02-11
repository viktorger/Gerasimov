package com.viktorger.tinkofffintechandroid.di

import android.content.Context
import com.viktorger.tinkofffintechandroid.database.TFDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesTFDatabase(applicationContext: Context): TFDatabase = TFDatabase
        .create(applicationContext)

}