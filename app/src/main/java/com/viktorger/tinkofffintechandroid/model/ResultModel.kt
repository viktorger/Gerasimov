package com.viktorger.tinkofffintechandroid.model

import java.lang.Exception

sealed class ResultModel<out T> {
    class Success<T>(val data: T): ResultModel<T>()
    class Error<T>(val exception: Exception): ResultModel<T>()
    data object Loading: ResultModel<Nothing>()
}