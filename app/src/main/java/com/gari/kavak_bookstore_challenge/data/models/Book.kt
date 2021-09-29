package com.gari.kavak_bookstore_challenge.data.models

data class Book(
    val isbn: String,
    val title: String,
    val author: String,
    val description: String,
    val genre: BookGenre,
    val img: String
)