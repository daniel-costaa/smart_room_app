package com.example.smartroom.common

import androidx.annotation.NonNull
import androidx.annotation.Nullable

class Resource<T> private constructor(@NonNull val status: Status,
                                      @Nullable val data: T?,
                                      @Nullable val message: String?) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {

        fun <T> success(@NonNull data: T): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, @Nullable data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(@Nullable data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }
}