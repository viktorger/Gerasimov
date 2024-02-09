package com.viktorger.tinkofffintechandroid.presentation.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import com.viktorger.tinkofffintechandroid.data.MovieRepository

class PopularViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun hello() {
        Log.d("TAG", "hello")
    }

}