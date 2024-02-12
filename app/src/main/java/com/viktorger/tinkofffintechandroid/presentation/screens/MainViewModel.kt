package com.viktorger.tinkofffintechandroid.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viktorger.tinkofffintechandroid.model.MovieDetails
import com.viktorger.tinkofffintechandroid.model.ResultModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _showButtonsStateFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(true)
    val showButtonsStateFlow: StateFlow<Boolean> = _showButtonsStateFlow

    fun shouldShowButtons(boolean: Boolean) {
        _showButtonsStateFlow.value = boolean
    }

}