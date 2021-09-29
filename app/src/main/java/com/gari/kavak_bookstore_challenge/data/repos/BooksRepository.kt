package com.gari.kavak_bookstore_challenge.data.repos

import com.gari.kavak_bookstore_challenge.data.DataResult
import com.gari.kavak_bookstore_challenge.data.models.Book
import com.gari.kavak_bookstore_challenge.network.apis.BooksApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BooksRepository @Inject constructor(private val booksApi: BooksApi) {

    fun getAllBooks(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        result: (DataResult<List<Book>>) -> Unit
    ) {
        coroutineScope.launch(coroutineDispatcher) {
            result(DataResult.Loading)
            booksApi.getAll().await().let { response ->
                response.body()?.takeIf { response.isSuccessful }?.let { body ->
                    result(DataResult.Success(body.results.books))
                } ?: result(DataResult.Error(Exception(response.message())))
            }
        }
    }

    fun getBestSellers(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        result: (DataResult<List<String>>) -> Unit
    ) {
        coroutineScope.launch(coroutineDispatcher) {
            result(DataResult.Loading)
            booksApi.getBestSellers().await().let { response ->
                response.body()?.takeIf { response.isSuccessful }?.let { body ->
                    result(DataResult.Success(body.results.bestSellers))
                } ?: result(DataResult.Error(Exception(response.message())))
            }
        }
    }
}
