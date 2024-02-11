package com.viktorger.tinkofffintechandroid.presentation.popular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.viktorger.tinkofffintechandroid.data.MovieRepository
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PopularViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    /*val movieShortcuts: StateFlow<PagingData<MovieShortcut>> = movieRepository
        .getTopMovieShortcutResultStream()
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())*/
    //val movieShortcuts: Flow<PagingData<MovieShortcut>> get() = _movieShortcuts.filterNotNull()

    val movieShortcutFlow = movieRepository.getTopMovieShortcutResultStream()
        .cachedIn(viewModelScope)
    init {
        refreshData()
    }


    fun refreshData() {

        /*viewModelScope.launch {
            try {
                _movieShortcuts.value = movieRepository.getTopMovieShortcutResultStream()
                    .cachedIn(viewModelScope).first()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/
    }

}