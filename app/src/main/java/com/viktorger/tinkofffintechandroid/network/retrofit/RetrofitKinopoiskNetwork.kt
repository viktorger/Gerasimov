package com.viktorger.tinkofffintechandroid.network.retrofit

import com.viktorger.tinkofffintechandroid.network.KinopoiskNetworkDataSource
import com.viktorger.tinkofffintechandroid.network.model.NetworkMovieShortcut
import javax.inject.Inject
import com.viktorger.tinkofffintechandroid.network.model.NetworkMovieResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private interface KinopoiskService {
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("api/v2.2/films/collections")
    suspend fun getFilmsTop(@Query("page") page: Int): NetworkMovieResponse
}

private const val KINOPOISK_BASE_URL = "https://kinopoiskapiunofficial.tech"

class RetrofitKinopoiskNetwork @Inject constructor() : KinopoiskNetworkDataSource {
    private val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    private val kinopoiskService = Retrofit.Builder()
        .baseUrl(KINOPOISK_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(KinopoiskService::class.java)

    override suspend fun getFilmsTop(page: Int): List<NetworkMovieShortcut> =
        kinopoiskService.getFilmsTop(page).items

}