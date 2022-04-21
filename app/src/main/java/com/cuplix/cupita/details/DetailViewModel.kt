package com.cuplix.cupita.details

import androidx.lifecycle.ViewModel
import com.cuplix.cupita.core.domain.model.Movie
import com.cuplix.cupita.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) =
        movieUseCase.setMovieFavorite(movie, newStatus)

}