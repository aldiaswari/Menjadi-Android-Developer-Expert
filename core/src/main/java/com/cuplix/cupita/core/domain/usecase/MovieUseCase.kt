package com.cuplix.cupita.core.domain.usecase

import com.cuplix.cupita.core.data.Resource
import com.cuplix.cupita.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getAllMovies(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getSearchMovies(search: String): Flow<List<Movie>>

    fun setMovieFavorite(movie: Movie, state: Boolean)
}