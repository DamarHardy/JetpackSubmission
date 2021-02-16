package com.damar.jetpacksubmission.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.IOException

sealed class NetworkResponse<out T: Any, out U: Any> {
    data class Success<T: Any>(val body: T): NetworkResponse<T, Nothing>()
    data class APIError<U: Any>(val body: U, val code: Int): NetworkResponse<Nothing, U>()
    data class NetworkError(val error: IOException): NetworkResponse<Nothing, Nothing>()
    data class UnknownError(val error: Throwable): NetworkResponse<Nothing, Nothing>()
}

@JsonClass(generateAdapter = true)
data class Error(
    @Json(name = "status")
    val status: String,

    @Json(name = "message")
    val message: String
)