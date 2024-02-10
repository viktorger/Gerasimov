package com.viktorger.tinkofffintechandroid.presentation.popular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viktorger.tinkofffintechandroid.data.MovieRepository
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import kotlinx.coroutines.launch

class PopularViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movieListLiveData: MutableLiveData<List<MovieShortcut>> = MutableLiveData(listOf())
    val movieListLiveData: LiveData<List<MovieShortcut>> = _movieListLiveData

    fun loadNetworkData() {
        viewModelScope.launch {
            movieRepository.getMoviesPage(1).collect {
                _movieListLiveData.postValue(it)
            }
        }
    }

}