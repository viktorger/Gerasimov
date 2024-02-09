package com.viktorger.tinkofffintechandroid

import android.app.Application
import com.viktorger.tinkofffintechandroid.di.AppComponent
import com.viktorger.tinkofffintechandroid.di.DaggerAppComponent

class TFApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

}