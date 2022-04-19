package com.cuplix.cupita.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("vote_average")
    val voteAverage: Double


)