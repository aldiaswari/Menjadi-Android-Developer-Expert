package com.cuplix.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cuplix.cupita.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {
    val favMovie = movieUseCase.getFavoriteMovies().asLiveData()
}