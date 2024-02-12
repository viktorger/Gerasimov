package com.viktorger.tinkofffintechandroid.presentation.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.viktorger.tinkofffintechandroid.data.MovieRepository
import javax.inject.Inject


class FavoriteViewModelFactory @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(movieRepository) as T
    }
}