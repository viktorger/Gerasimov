package com.viktorger.tinkofffintechandroid.presentation.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.viktorger.tinkofffintechandroid.data.MovieRepository
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import com.viktorger.tinkofffintechandroid.model.ResultModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class FavoriteViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movieShortcutStateFlow: StateFlow<ResultModel<List<MovieShortcut>>> = movieRepository
        .getFavoriteMoviesShortcuts()
        .map {
            if (it.isNotEmpty())
                ResultModel.Success(it)
            else
                ResultModel.Error(Exception("Empty list"))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ResultModel.Loading
        )

}