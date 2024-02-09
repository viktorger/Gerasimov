package com.viktorger.tinkofffintechandroid.di

import com.viktorger.tinkofffintechandroid.presentation.MainActivity
import com.viktorger.tinkofffintechandroid.presentation.popular.PopularFragment
import dagger.Component

@Component(modules = [DataModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(fragment: PopularFragment)
}