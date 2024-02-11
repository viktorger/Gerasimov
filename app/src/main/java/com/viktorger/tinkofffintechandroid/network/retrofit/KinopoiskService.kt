package com.viktorger.tinkofffintechandroid.network.retrofit

import com.viktorger.tinkofffintechandroid.network.model.NetworkMovieDetailsResponse
import com.viktorger.tinkofffintechandroid.network.model.NetworkMovieResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskService {
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("api/v2.2/films/top")
    suspend fun getMoviesTop(
        @Query("page") page: Int,
        @Query("type") type: String = "TOP_100_POPULAR_FILMS"
    ): Response<NetworkMovieResponse>

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("api/v2.2/films/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int): Response<NetworkMovieDetailsResponse>


    companion object {
        private const val KINOPOISK_BASE_URL = "https://kinopoiskapiunofficial.tech"

        fun create(): KinopoiskService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(KINOPOISK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(KinopoiskService::class.java)
        }

    }
}