package com.gari.kavak_bookstore_challenge.data

sealed class DataResult<out R> {
    object Loading : DataResult<Nothing>()
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val exception: Exception) : DataResult<Nothing>()
}
