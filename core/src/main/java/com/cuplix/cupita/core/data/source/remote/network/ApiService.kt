package com.cuplix.cupita.core.data.source.remote.network


import com.cuplix.cupita.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
    ): ListMovieResponse


}