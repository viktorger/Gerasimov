package com.viktorger.tinkofffintechandroid.data

import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import com.viktorger.tinkofffintechandroid.network.KinopoiskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultMovieRepository @Inject constructor(
    private val kinopoiskService: KinopoiskService
) : MovieRepository {
    override fun getMoviesPage(page: Int): Flow<MovieShortcut> = flow {
        kinopoiskService.getFilmsTop(1)
    }
}