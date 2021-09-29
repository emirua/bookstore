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
    private val bestSellersAdapter = BooksAdapter()
    private val historyBooksAdapter = BooksAdapter()
    private val scienceBooksAdapter = BooksAdapter()
    private val businessBooksAdapter = BooksAdapter()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding ?: throw UninitializedPropertyAccessException()

    private val bestSellersObserver = Observer<List<Book>?> {
        bestSellersAdapter.submitList(it)
    }
    private val historyBooksObserver = Observer<List<Book>?> {
        historyBooksAdapter.submitList(it)
    }
    private val scienceBooksObserver = Observer<List<Book>?> {
        scienceBooksAdapter.submitList(it)
    }
    private val businessBooksObserver = Observer<List<Book>?> {
        businessBooksAdapter.submitList(it)
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
            bestSellersRecyclerView.adapter = bestSellersAdapter
            historyRecyclerView.adapter = historyBooksAdapter
            scienceRecyclerView.adapter = scienceBooksAdapter
            businessRecyclerView.adapter = businessBooksAdapter
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
        mainViewModel.bestSellersbooks.observe(viewLifecycleOwner, bestSellersObserver)
        mainViewModel.historyBooks.observe(viewLifecycleOwner, historyBooksObserver)
        mainViewModel.scienceBooks.observe(viewLifecycleOwner, scienceBooksObserver)
        mainViewModel.businessBooks.observe(viewLifecycleOwner, businessBooksObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}