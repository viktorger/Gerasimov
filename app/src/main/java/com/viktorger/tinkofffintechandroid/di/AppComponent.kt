package com.viktorger.tinkofffintechandroid.di

import android.content.Context
import com.viktorger.tinkofffintechandroid.presentation.screens.details.MovieDetailsFragment
import com.viktorger.tinkofffintechandroid.presentation.screens.favorite.FavoriteFragment
import com.viktorger.tinkofffintechandroid.presentation.screens.popular.PopularFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataModule::class, NetworkModule::class, DatabaseModule::class, DaosModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun inject(fragment: PopularFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: MovieDetailsFragment)
}