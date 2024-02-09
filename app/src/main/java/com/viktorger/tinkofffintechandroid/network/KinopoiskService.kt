package com.viktorger.tinkofffintechandroid.network

import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KinopoiskService {

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/top")
    suspend fun getFilmsTop(@Query("page") page: Int): Response<MovieShortcut>

}