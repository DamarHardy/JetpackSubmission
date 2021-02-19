package com.damar.jetpacksubmission.utils

sealed class DataState<out R>{
    data class Success <out T> (val body: T): DataState<T>()
    data class Error(val e: String): DataState<Nothing>()
    object Loading: DataState<Nothing>()
}
