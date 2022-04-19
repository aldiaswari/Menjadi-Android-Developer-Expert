package com.cuplix.cupita.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cuplix.cupita.core.data.Resource
import com.cuplix.cupita.core.domain.model.Movie
import com.cuplix.cupita.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class MovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val queryChannel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(search: String) {
        queryChannel.offer(search)
    }

    val movieResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieUseCase.getSearchMovies(it)
        }.asLiveData()


    fun getMovies(): LiveData<Resource<List<Movie>>> =
        movieUseCase.getAllMovies().asLiveData()
}