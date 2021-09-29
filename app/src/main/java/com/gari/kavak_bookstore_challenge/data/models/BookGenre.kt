package com.gari.kavak_bookstore_challenge.data.models

import com.google.gson.annotations.SerializedName

enum class BookGenre {
    @SerializedName("History")
    HISTORY,
    @SerializedName("Science")
    SCIENCE,
    @SerializedName("Business")
    BUSINESS
}
