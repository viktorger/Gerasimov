package com.viktorger.tinkofffintechandroid.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import com.viktorger.tinkofffintechandroid.model.ResultModel
import com.viktorger.tinkofffintechandroid.network.model.asExternalModel
import com.viktorger.tinkofffintechandroid.network.retrofit.KinopoiskService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MovieShortcutPagingSource(
    private val kinopoiskService: KinopoiskService
) : PagingSource<Int, MovieShortcut>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieShortcut> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = kinopoiskService.getMoviesTop(page = page)

            if (response.isSuccessful) {
                val apiCallResponseBody = response.body()!!

                LoadResult.Page(
                    data = apiCallResponseBody.films.map { it.asExternalModel() },
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (page == apiCallResponseBody.pagesCount) null else page + 1
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieShortcut>): Int? {
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}