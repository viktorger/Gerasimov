package com.viktorger.tinkofffintechandroid.presentation.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viktorger.tinkofffintechandroid.data.MovieRepository
import com.viktorger.tinkofffintechandroid.model.MovieDetails
import com.viktorger.tinkofffintechandroid.model.ResultModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val defaultDispatcher = Dispatchers.Default
    private val _detailsLiveData: MutableLiveData<ResultModel<MovieDetails>> = MutableLiveData(
        ResultModel.Loading
    )
    val detailsLiveData: LiveData<ResultModel<MovieDetails>> = _detailsLiveData

    fun getDetails(movieId: Int) {
        _detailsLiveData.value = ResultModel.Loading

        viewModelScope.launch(defaultDispatcher) {
            val result = movieRepository.getMovieDetails(movieId)
            _detailsLiveData.postValue(result)
        }

    }
}