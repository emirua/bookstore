package com.gari.kavak_bookstore_challenge.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gari.kavak_bookstore_challenge.R
import com.gari.kavak_bookstore_challenge.data.models.Book
import com.gari.kavak_bookstore_challenge.databinding.MainFragmentBinding
import com.gari.kavak_bookstore_challenge.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val booksAdapter = BooksAdapter()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding ?: throw UninitializedPropertyAccessException()

    private val postsObserver = Observer<List<Book>?> {
        booksAdapter.submitList(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MainFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar.title = getString(R.string.book_store)
            productsRecyclerView.adapter = booksAdapter
            swipeRefreshLayout.setOnRefreshListener {
                mainViewModel.fetchBooks()
            }
            mainViewModel.fetchBooks()
            mainViewModel.fetchBooksResult.observe(viewLifecycleOwner) {
                when (it) {
                    FetchBooksResult.Error -> {
                        swipeRefreshLayout.isRefreshing = false
                        root.snackBar(R.string.error)
                    }
                    FetchBooksResult.Loading -> swipeRefreshLayout.isRefreshing = true
                    FetchBooksResult.Success -> swipeRefreshLayout.isRefreshing = false
                }
            }
        }
        mainViewModel.books.observe(viewLifecycleOwner, postsObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}