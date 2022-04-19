package com.cuplix.cupita.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var title: String,
    var voteCount: Int,
    var posterPath: String,
    var backdropPath: String,
    var overview: String,
    var originalLanguage: String,
    var releaseDate: String,
    var popularity: Double,
    var voteAverage: Double,
    var isFavorite: Boolean = false
) : Parcelable

