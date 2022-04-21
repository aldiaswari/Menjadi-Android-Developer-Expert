package com.cuplix.cupita.core.domain.usecase

import com.cuplix.cupita.core.data.Resource
import com.cuplix.cupita.core.domain.model.Movie
import com.cuplix.cupita.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val iMovieRepository: IMovieRepository) : MovieUseCase {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        iMovieRepository.getAllMovies()


    override fun getFavoriteMovies(): Flow<List<Movie>> =
        iMovieRepository.getFavoriteMovies()

    override fun getSearchMovies(search: String): Flow<List<Movie>> =
        iMovieRepository.getSearchMovies(search)

    override fun setMovieFavorite(movie: Movie, state: Boolean) =
        iMovieRepository.setMovieFavorite(movie, state)

}