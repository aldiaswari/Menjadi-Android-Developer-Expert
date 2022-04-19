package com.cuplix.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cuplix.cupita.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favMovie = movieUseCase.getFavoriteMovies().asLiveData()
}