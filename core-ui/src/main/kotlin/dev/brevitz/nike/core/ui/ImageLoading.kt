package dev.brevitz.nike.core.ui

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.load(url: String) {
    Picasso.get()
        .load(url)
        .fit()
        .centerCrop()
        .into(this)
}
