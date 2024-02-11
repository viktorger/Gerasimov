package com.viktorger.tinkofffintechandroid.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.viktorger.tinkofffintechandroid.database.dao.MovieFavoriteDetailsDao
import com.viktorger.tinkofffintechandroid.database.dao.MovieFavoriteShortcutDao
import com.viktorger.tinkofffintechandroid.database.entities.MovieFavoriteShortcutEntity
import com.viktorger.tinkofffintechandroid.database.entities.asExternalModel
import com.viktorger.tinkofffintechandroid.database.entities.asFavoriteEntity
import com.viktorger.tinkofffintechandroid.model.MovieDetails
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import com.viktorger.tinkofffintechandroid.model.ResultModel
import com.viktorger.tinkofffintechandroid.network.model.asEntity
import com.viktorger.tinkofffintechandroid.network.model.asExternalModel
import com.viktorger.tinkofffintechandroid.network.retrofit.KinopoiskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val PAGE_SIZE = 20

@Singleton
class DefaultMovieRepository @Inject constructor(
    private val kinopoiskService: KinopoiskService,
    private val movieFavoriteDetailsDao: MovieFavoriteDetailsDao,
    private val movieFavoriteShortcutDao: MovieFavoriteShortcutDao
) : MovieRepository {

    override suspend fun getTopMovieShortcutResultStream(): Flow<PagingData<MovieShortcut>> {
        val favoriteIds = movieFavoriteShortcutDao.getAllIds()
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PAGE_SIZE
            )
        ) {
            MovieShortcutPagingSource(kinopoiskService)
        }.flow.map { pagingData ->
            pagingData.map { movieShortcut ->
                movieShortcut.apply { isFavorite = id in favoriteIds }
            }
        }
    }



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

    override fun saveMovieToFavorites(movieShortcut: MovieShortcut): Flow<Boolean> = flow {
        val response = kinopoiskService.getMovieDetails(movieId = movieShortcut.id)
        val apiCallResponseBody = response.body()!!

        movieFavoriteDetailsDao.insertDetails(apiCallResponseBody.asEntity())
        movieFavoriteShortcutDao.insertDetails(movieShortcut.asFavoriteEntity())

        emit(true)
    }.catch {
        emit(false)
    }

    override suspend fun getFavoriteMoviesShortcuts(): ResultModel<List<MovieShortcut>> =
        ResultModel.Success(movieFavoriteShortcutDao.getAll().map { it.asExternalModel() })

    override suspend fun getFavoriteMovieDetails(id: Int): ResultModel<MovieDetails> =
        ResultModel.Success(movieFavoriteDetailsDao.getDetailsById(id).asExternalModel())

}


