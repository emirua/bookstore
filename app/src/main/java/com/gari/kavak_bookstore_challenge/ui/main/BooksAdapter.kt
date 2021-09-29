package com.gari.kavak_bookstore_challenge.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gari.kavak_bookstore_challenge.data.models.Book
import com.gari.kavak_bookstore_challenge.databinding.BooksAdapterItemBinding
import com.gari.kavak_bookstore_challenge.utils.visibleIf
import com.squareup.picasso.Picasso

class BooksAdapter :
    ListAdapter<Book, BooksAdapter.ItemViewHolder>(itemsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = BooksAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        getItem(position).let { viewHolder.bind(it) }
        viewHolder.binding.lineSeparator.visibleIf(position != itemCount - 1)
    }

    class ItemViewHolder(
        val binding: BooksAdapterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            with(binding) {
                bookTitle.text = book.title
                bookAuthor.text = book.author
                Picasso.get()
                    .load(book.img.takeIf { it.isNotEmpty() })
                    .into(bookImage)
            }
        }
    }
}

val itemsDiffCallback = object : DiffUtil.ItemCallback<Book>() {

    override fun areItemsTheSame(
        oldItem: Book,
        newItem: Book
    ) = oldItem.isbn == newItem.isbn

    override fun areContentsTheSame(
        oldItem: Book,
        newItem: Book
    ): Boolean = oldItem == newItem
}
