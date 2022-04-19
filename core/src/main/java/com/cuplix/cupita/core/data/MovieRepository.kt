package com.cuplix.cupita.core.data

import com.cuplix.cupita.core.data.source.local.LocalDataSource
import com.cuplix.cupita.core.data.source.remote.RemoteDataSource
import com.cuplix.cupita.core.data.source.remote.response.MovieResponse
import com.cuplix.cupita.core.data.source.remote.vo.ApiResponse
import com.cuplix.cupita.core.domain.model.Movie
import com.cuplix.cupita.core.domain.repository.IMovieRepository
import com.cuplix.cupita.core.utils.AppExecutors
import com.cuplix.cupita.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()



    override fun getSearchMovies(search: String): Flow<List<Movie>> {
        return localDataSource.getMovieSearch(search).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }



    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getAllFavoriteMovies().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }


    override fun setMovieFavorite(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.setMovieFavorite(movieEntity, state)
        }
    }
}