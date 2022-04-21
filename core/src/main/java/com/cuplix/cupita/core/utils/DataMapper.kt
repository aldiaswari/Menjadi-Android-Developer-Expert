package com.cuplix.cupita.core.utils

import com.cuplix.cupita.core.data.source.local.entity.MovieEntity
import com.cuplix.cupita.core.data.source.remote.response.MovieResponse
import com.cuplix.cupita.core.domain.model.Movie

object DataMapper {
    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                it.id,
                it.title,
                it.voteCount,
                it.posterPath,
                it.backdropPath,
                it.overview,
                it.originalLanguage,
                it.releaseDate,
                it.popularity,
                it.voteAverage,
                isFavorite = false,
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> {
        return input.map {
            Movie(
                it.id,
                it.title,
                it.voteCount,
                it.posterPath,
                it.backdropPath,
                it.overview,
                it.originalLanguage,
                it.releaseDate,
                it.popularity,
                it.voteAverage,
                isFavorite = it.isFavorite,
            )
        }
    }

    fun mapDomainToEntity(input: Movie): MovieEntity {
        return MovieEntity(
            input.id,
            input.title,
            input.voteCount,
            input.posterPath,
            input.backdropPath,
            input.overview,
            input.originalLanguage,
            input.releaseDate,
            input.popularity,
            input.voteAverage,
            isFavorite = input.isFavorite        )
    }
}
