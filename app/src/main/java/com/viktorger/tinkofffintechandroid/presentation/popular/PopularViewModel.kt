package com.viktorger.tinkofffintechandroid.presentation.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.viktorger.tinkofffintechandroid.data.MovieRepository
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PopularViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    /*val movieShortcutFlow = movieRepository.getTopMovieShortcutResultStream()
        .cachedIn(viewModelScope)*/

    private val _movieShortcutStateFlow: MutableStateFlow<PagingData<MovieShortcut>> =
        MutableStateFlow(PagingData.empty())
    val movieShortcutStateFlow: StateFlow<PagingData<MovieShortcut>> = _movieShortcutStateFlow

    init {
        getMoviePreviews()
    }

    fun addToFavorite(movieShortcut: MovieShortcut) {
        viewModelScope.launch {
            movieRepository.saveMovieToFavorites(movieShortcut).collect {
                Log.d("TAG", "$it")
            }
        }
    }

    private fun getMoviePreviews() {
        viewModelScope.launch {
            _movieShortcutStateFlow.value =
                movieRepository.getTopMovieShortcutResultStream().cachedIn(viewModelScope).first()
        }
    }

}
