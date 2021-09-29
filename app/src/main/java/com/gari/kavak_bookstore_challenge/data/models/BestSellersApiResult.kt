package com.gari.kavak_bookstore_challenge.data.models

import com.google.gson.annotations.SerializedName

data class BestSellersApiResult(
    val results: BestSellersResult,
)

data class BestSellersResult(
    @SerializedName("best_sellers")
    val bestSellers: List<String>
)

