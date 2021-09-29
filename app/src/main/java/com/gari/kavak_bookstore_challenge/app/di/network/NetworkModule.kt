package com.gari.kavak_bookstore_challenge.app.di.network

import com.gari.kavak_bookstore_challenge.network.RestAdapter
import com.gari.kavak_bookstore_challenge.network.apis.BooksApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {

    @Provides
    fun provideRestAdapter(): Retrofit =
        RestAdapter.provideRestAdapter()

    @Provides
    fun providePostsService(restAdapter: Retrofit): BooksApi =
        restAdapter.create(BooksApi::class.java)
}
