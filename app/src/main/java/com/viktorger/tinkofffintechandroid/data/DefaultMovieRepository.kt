package com.viktorger.tinkofffintechandroid.data

import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import com.viktorger.tinkofffintechandroid.network.KinopoiskNetworkDataSource
import com.viktorger.tinkofffintechandroid.network.model.NetworkMovieShortcut
import com.viktorger.tinkofffintechandroid.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultMovieRepository @Inject constructor(
    private val kinopoiskNetworkDataSource: KinopoiskNetworkDataSource
) : MovieRepository {

    override fun getMoviesPage(page: Int): Flow<List<MovieShortcut>> = flow {
        emit(kinopoiskNetworkDataSource.getFilmsTop(1)
            .map { it.asExternalModel() })
    }
}