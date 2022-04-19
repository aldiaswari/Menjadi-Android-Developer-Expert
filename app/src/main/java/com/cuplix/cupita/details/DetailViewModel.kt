package com.cuplix.cupita.details

import androidx.lifecycle.ViewModel
import com.cuplix.cupita.core.domain.model.Movie
import com.cuplix.cupita.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) =
        movieUseCase.setMovieFavorite(movie, newStatus)

}