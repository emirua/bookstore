package com.gari.kavak_bookstore_challenge.ui.main

import androidx.lifecycle.*
import com.gari.kavak_bookstore_challenge.data.DataResult
import com.gari.kavak_bookstore_challenge.data.models.Book
import com.gari.kavak_bookstore_challenge.data.repos.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val booksRepository: BooksRepository,
) : ViewModel() {

    private val _books = MutableLiveData<List<Book>?>()
    val books: LiveData<List<Book>?> = _books
    val fetchBooksResult = MutableLiveData<FetchBooksResult>()

    fun fetchBooks() {

        booksRepository.getAllBooks(viewModelScope) { result ->
            when (result) {
                DataResult.Loading -> {
                    fetchBooksResult.postValue(FetchBooksResult.Loading)
                }
                is DataResult.Success -> {
                    _books.postValue(result.data.filter { it.img.isNotEmpty() })
                    fetchBooksResult.postValue(FetchBooksResult.Success)
                }
                is DataResult.Error -> {
                    fetchBooksResult.postValue(FetchBooksResult.Error)
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
