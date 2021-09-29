package com.gari.kavak_bookstore_challenge.network.apis

import com.gari.kavak_bookstore_challenge.data.models.BestSellersApiResult
import com.gari.kavak_bookstore_challenge.data.models.BooksApiResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BooksApi {
    @GET(ALL_BOOKS_URL)
    fun getAll(): Deferred<Response<BooksApiResult>>

    @GET(BEST_SELLERS_URL)
    fun getBestSellers(): Deferred<Response<BestSellersApiResult>>

}

const val BOOKS_BASE_URL = "files/main"
const val ALL_BOOKS_URL = "$BOOKS_BASE_URL/books.json"
const val BEST_SELLERS_URL = "$BOOKS_BASE_URL/best_sellers.json"
