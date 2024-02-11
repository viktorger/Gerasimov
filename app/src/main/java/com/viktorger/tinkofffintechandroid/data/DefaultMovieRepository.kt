package com.viktorger.tinkofffintechandroid.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.viktorger.tinkofffintechandroid.model.MovieDetails
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import com.viktorger.tinkofffintechandroid.model.ResultModel
import com.viktorger.tinkofffintechandroid.network.model.asExternalModel
import com.viktorger.tinkofffintechandroid.network.retrofit.KinopoiskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val PAGE_SIZE = 20

class DefaultMovieRepository @Inject constructor(
    private val kinopoiskService: KinopoiskService
) : MovieRepository {

    override fun getTopMovieShortcutResultStream(): Flow<PagingData<MovieShortcut>> =
        Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PAGE_SIZE
            )
        ) {
            MovieShortcutPagingSource(kinopoiskService)
        }.flow


    override suspend fun getMovieDetails(movieId: Int): ResultModel<MovieDetails> =
        withContext(Dispatchers.IO) {
            try {
                val response = kinopoiskService.getMovieDetails(movieId)

                if (response.isSuccessful) {
                    val apiCallResponseBody = response.body()!!
                    ResultModel.Success(apiCallResponseBody.asExternalModel())
                } else {
                    ResultModel.Error(
                        HttpException(response)
                    )
                }
            } catch (e: HttpException) {
                ResultModel.Error(e)
            } catch (e: IOException) {
                ResultModel.Error(e)
            }
        }

    /*private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>)
            : ResultModel<T> = withContext(Dispatchers.IO) {
        try {
            val apiCallResult = apiCall()

            if (apiCallResult.isSuccessful) {
                val apiCallResultBody = apiCallResult.body()!!
                ResultModel.Success(apiCallResultBody)
            } else {
                ResultModel.Error(
                    HttpException(apiCallResult)
                )
            }
        } catch (e: HttpException) {
            ResultModel.Error(e)
        } catch (e: IOException) {
            ResultModel.Error(e)
        }
    }*/
}

