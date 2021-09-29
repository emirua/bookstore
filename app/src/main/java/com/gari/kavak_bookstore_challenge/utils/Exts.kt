package com.gari.kavak_bookstore_challenge.utils

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


fun View.visibleIf(predicate: Boolean) {
    this.visibility = if (predicate) View.VISIBLE else View.GONE
}

fun View.snackBar(@StringRes stringId: Int, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, stringId, length).show()
}