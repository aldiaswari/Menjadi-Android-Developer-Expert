package com.cuplix.cupita.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cuplix.cupita.R
import com.cuplix.cupita.core.BuildConfig.IMAGE_URL
import com.cuplix.cupita.core.domain.model.Movie
import com.cuplix.cupita.core.utils.Helper.loadFromUrl
import com.cuplix.cupita.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


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
        populateDetail(movie)

        detailsBinding.backButton.setOnClickListener { onBackPressed() }

    }

    private fun populateDetail(movie: Movie?) {
        movie?.let {
            detailsBinding.titleDetail.text = movie.title
            detailsBinding.date.text = movie.releaseDate
            detailsBinding.overview.text = movie.overview
            detailsBinding.popularity.text = getString(
                R.string.popularity_detail,
                movie.popularity.toString(),
                movie.voteCount.toString(),
                movie.voteAverage.toString()
            )
            detailsBinding.userScore.text = movie.voteAverage.toString()

            detailsBinding.posterTopBar.loadFromUrl(IMAGE_URL + movie.backdropPath)
            detailsBinding.subPoster.loadFromUrl(IMAGE_URL + movie.posterPath)

            var favoriteState = movie.isFavorite
            setFavoriteState(favoriteState)
            detailsBinding.favoriteButton.setOnClickListener {
                favoriteState = !favoriteState
                viewModel.setFavoriteMovie(movie, favoriteState)
                setFavoriteState(favoriteState)
            }
        }
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