package com.viktorger.tinkofffintechandroid.presentation.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.viktorger.tinkofffintechandroid.data.MovieRepository
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PopularViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    /*val movieShortcutFlow = movieRepository.getTopMovieShortcutResultStream()
        .cachedIn(viewModelScope)*/

    private val _movieShortcutStateFlow: MutableStateFlow<PagingData<MovieShortcut>> =
        MutableStateFlow(PagingData.empty())
    val movieShortcutStateFlow: StateFlow<PagingData<MovieShortcut>> = _movieShortcutStateFlow

    private val _shouldUpdateItem: MutableStateFlow<Int> = MutableStateFlow(SHOULD_NOT_UPDATE)
    val shouldUpdateItem: StateFlow<Int> = _shouldUpdateItem

    init {
        getMovieShortcuts()
    }

    fun addToFavorite(movieShortcut: MovieShortcut, position: Int) {
        viewModelScope.launch {
            movieRepository.saveMovieToFavorites(movieShortcut).collect { bool ->
                _movieShortcutStateFlow.update { pagingData ->
                    pagingData.map {
                        if (it.id == movieShortcut.id)
                            it.apply {
                                isFavorite = bool
                            }
                        else it
                    }
                }
                if (bool) {
                    _shouldUpdateItem.value = position
                }
            }
        }
    }

    private fun getMovieShortcuts() {
        viewModelScope.launch {
            _movieShortcutStateFlow.value =
                movieRepository.getTopMovieShortcutResultStream().cachedIn(viewModelScope).first()
        }
    }

}
