package com.cuplix.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuplix.cupita.core.ui.MovieAdapter
import com.cuplix.cupita.core.utils.Status
import com.cuplix.cupita.details.DetailsActivity
import com.cuplix.cupita.di.FavoriteModuleDependencies
import com.cuplix.favorite.databinding.FragmentFavoriteBinding
import com.cuplix.favorite.di.DaggerFavoriteModule
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject



@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding
    private lateinit var movieAdapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        observerFavMovie()
        swipeRefresh()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteModule.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)


    }


    private fun swipeRefresh() {
        binding.favSwipeRefresh.setOnRefreshListener {

            Handler(Looper.getMainLooper()).postDelayed({
                observerFavMovie()
                binding.favSwipeRefresh.isRefreshing = false
            }, 2000)
        }


    }

    private fun observerFavMovie() {
        favoriteViewModel.favMovie.observe(viewLifecycleOwner, { movies ->
            if (movies.isNullOrEmpty()) {
                setDataState(Status.ERROR)
            } else {
                setDataState(Status.SUCCESS)
            }
            movieAdapter.setData(movies)
            Log.d("Favorite", "data " + movieAdapter.setData(movies))

            })
    }

    private fun setDataState(status: Status) {
        when (status) {
            Status.ERROR -> {
                binding.notFound.visibility = View.VISIBLE
                binding.notFound.visibility = View.VISIBLE
            }
            Status.LOADING -> {
                binding.notFound.visibility = View.VISIBLE
                binding.notFound.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                binding.notFound.visibility = View.GONE
                binding.notFoundText.visibility = View.GONE
            }
        }
    }


    private fun initRecycler() {
        movieAdapter = MovieAdapter()
        binding.rvMovieFav.apply {

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }

        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMovieFav.adapter = null
        _binding = null
    }

}