package com.cuplix.cupita.details

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cuplix.cupita.R
import com.cuplix.cupita.core.BuildConfig.IMAGE_URL
import com.cuplix.cupita.core.domain.model.Movie
import com.cuplix.cupita.core.utils.Helper.loadFromUrl
import com.cuplix.cupita.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extraMovie"
    }

    private lateinit var detailsBinding: ActivityDetailsBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(detailsBinding.root)
        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)

        movie?.let {
            populateDetail(it)
        }

        detailsBinding.backButton.setOnClickListener { onBackPressed() }
    }

    private fun populateDetail(movie: Movie) {
        with(detailsBinding) {
            titleDetail.text = movie.title
            date.text = movie.releaseDate
            overview.text = movie.overview
            popularity.text = getString(
                R.string.popularity_detail,
                movie.popularity.toString(),
                movie.voteCount.toString(),
                movie.voteAverage.toString()
            )
            userScore.text = movie.voteAverage.toString()

            posterTopBar.loadFromUrl(IMAGE_URL + movie.backdropPath)
            subPoster.loadFromUrl(IMAGE_URL + movie.posterPath)

            var favoriteState = movie.isFavorite
            setFavoriteState(favoriteState)
            detailsBinding.favoriteButton.setOnClickListener {
                favoriteState = !favoriteState
                setFavorite(movie, favoriteState)
                setFavoriteState(favoriteState)
                Log.d("Favorite", "Add success ")
            }

        }
    }


    private fun setFavorite(movie: Movie, favoriteState: Boolean) {
        viewModel.setFavoriteMovie(movie, favoriteState)
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            detailsBinding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this.applicationContext,
                    R.drawable.ic_baseline_bookmark_added_success_24
                )
            )
        } else {
            detailsBinding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this.applicationContext,
                    R.drawable.ic_baseline_bookmark_add_24
                )
            )
        }
    }

    override fun onStop() {
        super.onStop()
        detailsBinding.posterTopBar.pause()
    }


}