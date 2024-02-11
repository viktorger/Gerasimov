package com.viktorger.tinkofffintechandroid.di

import android.content.Context
import com.viktorger.tinkofffintechandroid.database.TFDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun providesTFDatabase(applicationContext: Context): TFDatabase = TFDatabase
        .create(applicationContext)

}