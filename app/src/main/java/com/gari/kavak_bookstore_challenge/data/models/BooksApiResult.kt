package com.gari.kavak_bookstore_challenge.data.models

data class BooksApiResult(
    val results: BooksResult,
)

data class BooksResult(
    val books: List<Book>
)

