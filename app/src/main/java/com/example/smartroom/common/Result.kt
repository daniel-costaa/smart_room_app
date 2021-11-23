package com.example.smartroom.common

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(
        val t: Throwable,
        var consumed: Boolean = false
    ) : Result<Nothing>()
}
