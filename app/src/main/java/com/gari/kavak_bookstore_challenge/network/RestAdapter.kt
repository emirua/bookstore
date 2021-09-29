package com.gari.kavak_bookstore_challenge.network

import com.gari.kavak_bookstore_challenge.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestAdapter {

    var authToken: String? = null
    private var _restAdapter: Retrofit? = null

    fun provideRestAdapter() = createRestAdapter()

    private fun createRestAdapter(): Retrofit {

        val baseUrl = "https://raw.githubusercontent.com/ejgteja/"

        val client: OkHttpClient = buildClient()

        return buildRestAdapter(baseUrl, client, listOf()).also {
            _restAdapter = it
        }
    }

    private fun buildClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }

        return okHttpClient.build()
    }

    private fun buildRestAdapter(
        baseUrl: String,
        client: OkHttpClient,
        converterFactories: List<Converter.Factory>
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactories(converterFactories)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}

private fun Retrofit.Builder.addConverterFactories(converterFactories: List<Converter.Factory>): Retrofit.Builder {
    converterFactories.forEach {
        addConverterFactory(it)
    }
    return this
}
