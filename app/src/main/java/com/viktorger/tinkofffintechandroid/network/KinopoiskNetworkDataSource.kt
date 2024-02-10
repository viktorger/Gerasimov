package com.viktorger.tinkofffintechandroid.network

import com.viktorger.tinkofffintechandroid.network.model.NetworkMovieResponse
import com.viktorger.tinkofffintechandroid.network.model.NetworkMovieShortcut

interface KinopoiskNetworkDataSource {
    suspend fun getFilmsTop(page: Int): List<NetworkMovieShortcut>
}