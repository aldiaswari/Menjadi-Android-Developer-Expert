package com.cuplix.cupita.core.data.source.local

import com.cuplix.cupita.core.data.source.local.entity.MovieEntity
import com.cuplix.cupita.core.data.source.local.room.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val mMovieDao: MovieDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> = mMovieDao.getMovies()

    fun getAllFavoriteMovies(): Flow<List<MovieEntity>> = mMovieDao.getFavoriteMovies()

    fun getMovieSearch(search: String): Flow<List<MovieEntity>> {
        return mMovieDao.getSearchMovies(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    suspend fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovie(movies)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        mMovieDao.updateFavoriteMovie(movie)
    }
}