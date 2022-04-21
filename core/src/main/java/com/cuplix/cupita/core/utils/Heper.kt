package com.cuplix.cupita.core.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cuplix.cupita.core.R

object Helper {

    const val TYPE_MOVIE = "TYPE_MOVIE"
    const val ENDPOINT_POSTER_SIZE_W185 = "w185/"
    const val ENDPOINT_POSTER_SIZE_W780 = "w780"

    fun setImageWithGlide(context: Context, imagePath: String, imageView: ImageView) {
        Glide.with(context).clear(imageView)
        Glide.with(context).load(imagePath).into(imageView)
    }

    fun ImageView.loadFromUrl(path: String) {
        Glide.with(this).clear(this)
        Glide.with(this)
            .setDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
            ).load(path).into(this)
    }
}