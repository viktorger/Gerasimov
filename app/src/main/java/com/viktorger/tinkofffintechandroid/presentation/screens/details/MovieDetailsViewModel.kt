package com.viktorger.tinkofffintechandroid.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viktorger.tinkofffintechandroid.data.MovieRepository
import com.viktorger.tinkofffintechandroid.model.MovieDetails
import com.viktorger.tinkofffintechandroid.model.ResultModel
import com.viktorger.tinkofffintechandroid.presentation.model.NetworkStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieDetailsStateFlow: MutableStateFlow<ResultModel<MovieDetails>> =
        MutableStateFlow(ResultModel.Loading)
    val movieDetailsStateFlow: StateFlow<ResultModel<MovieDetails>> = _movieDetailsStateFlow

    fun getDetails(movieId: Int, networkStatus: NetworkStatus) {

        viewModelScope.launch {
            if (networkStatus == NetworkStatus.Online) {
                _movieDetailsStateFlow.value = movieRepository.getMovieDetails(movieId)
            } else {
                _movieDetailsStateFlow.value = movieRepository.getFavoriteMovieDetails(movieId)
            }
        }
    }
}