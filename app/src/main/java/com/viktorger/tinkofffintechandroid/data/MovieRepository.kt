package com.viktorger.tinkofffintechandroid.data

import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviesPage(page: Int): Flow<MovieShortcut>
}