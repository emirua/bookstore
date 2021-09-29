package com.gari.kavak_bookstore_challenge.ui.main

import androidx.lifecycle.*
import com.gari.kavak_bookstore_challenge.data.DataResult
import com.gari.kavak_bookstore_challenge.data.models.Book
import com.gari.kavak_bookstore_challenge.data.models.BookGenre
import com.gari.kavak_bookstore_challenge.data.repos.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val booksRepository: BooksRepository,
) : ViewModel() {

    private val _bestSellersbooks = MutableLiveData<List<Book>?>()
    val bestSellersbooks: LiveData<List<Book>?> = _bestSellersbooks
    private val _historyBooks = MutableLiveData<List<Book>?>()
    val historyBooks: LiveData<List<Book>?> = _historyBooks
    private val _scienceBooks = MutableLiveData<List<Book>?>()
    val scienceBooks: LiveData<List<Book>?> = _scienceBooks
    private val _businessBooks = MutableLiveData<List<Book>?>()
    val businessBooks: LiveData<List<Book>?> = _businessBooks
    val fetchBooksResult = MutableLiveData<FetchBooksResult>()

    fun fetchBooks() {

        booksRepository.getAllBooks(viewModelScope) { result ->
            when (result) {
                DataResult.Loading -> {
                    fetchBooksResult.postValue(FetchBooksResult.Loading)
                }
                is DataResult.Success -> {
                    fetchBestSellers(result.data)
                    _historyBooks.postValue(result.data.filter { it.genre == BookGenre.HISTORY})
                    _scienceBooks.postValue(result.data.filter { it.genre == BookGenre.SCIENCE})
                    _businessBooks.postValue(result.data.filter { it.genre == BookGenre.BUSINESS})
                }
                is DataResult.Error -> {
                    fetchBooksResult.postValue(FetchBooksResult.Error)
                }
            }
        }
    }

    private fun fetchBestSellers(booksList: List<Book>) {
        booksRepository.getBestSellers(viewModelScope) {result ->
            when(result) {
                is DataResult.Error -> {
                    fetchBooksResult.postValue(FetchBooksResult.Error)
                }

                DataResult.Loading -> {
                    fetchBooksResult.postValue(FetchBooksResult.Loading)
                }
                is DataResult.Success -> {
                    _bestSellersbooks.postValue(booksList.filter { it.isbn in result.data})
                    fetchBooksResult.postValue(FetchBooksResult.Success)
                }
            }
        }
    }
}

sealed class FetchBooksResult {
    object Loading : FetchBooksResult()
    object Success : FetchBooksResult()
    object Error : FetchBooksResult()
}
