package com.viktorger.tinkofffintechandroid.network.retrofit

import com.viktorger.tinkofffintechandroid.network.model.NetworkMovieDetailsResponse
import com.viktorger.tinkofffintechandroid.network.model.NetworkMovieResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskService {
    @GET("api/v2.2/films/top")
    suspend fun getMoviesTop(
        @Query("page") page: Int,
        @Query("type") type: String = "TOP_100_POPULAR_FILMS"
    ): Response<NetworkMovieResponse>

    @GET("api/v2.2/films/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int): Response<NetworkMovieDetailsResponse>


    companion object {
        private const val KINOPOISK_BASE_URL = "https://kinopoiskapiunofficial.tech"
        private const val X_API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

        fun create(): KinopoiskService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val headerInterceptor = Interceptor { chain ->
                val request: Request = chain.request()
                val newRequest: Request = request.newBuilder()
                    .addHeader("X-API-KEY", X_API_KEY)
                    .build()
                chain.proceed(newRequest)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
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