package com.viktorger.tinkofffintechandroid.data

import androidx.paging.PagingData
import com.viktorger.tinkofffintechandroid.model.MovieDetails
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import com.viktorger.tinkofffintechandroid.model.ResultModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTopMovieShortcutResultStream(): Flow<PagingData<MovieShortcut>>
    suspend fun getMovieDetails(movieId: Int): ResultModel<MovieDetails>
}