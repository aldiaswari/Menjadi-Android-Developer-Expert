package com.cuplix.cupita.movies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuplix.cupita.core.data.Resource
import com.cuplix.cupita.core.ui.MovieAdapter
import com.cuplix.cupita.core.utils.Status
import com.cuplix.cupita.databinding.FragmentMovieBinding
import com.cuplix.cupita.databinding.LayoutMovieBinding
import com.cuplix.cupita.details.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieFragment : Fragment() {

    companion object {
        val TAG: String = MovieFragment::class.java.simpleName
    }

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding as FragmentMovieBinding
    private lateinit var layMovieBinding: LayoutMovieBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        layMovieBinding = binding.layMovie
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMovie()
        observeMovie()
        observeSearchQuery()
        setSearchList()
        setRefresh()
    }

    private fun setRefresh() {
        layMovieBinding.homePageSwipeRefresh.setOnRefreshListener {

            Handler(Looper.getMainLooper()).postDelayed({
                observeMovie()
                layMovieBinding.homePageSwipeRefresh.isRefreshing = false
            }, 2000)
        }
    }

    private fun setupMovie() {

        movieAdapter = MovieAdapter()

        layMovieBinding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }

        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

    }


    private fun observeMovie() {
        viewModel.getMovies().observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        setDataState(Status.SUCCESS)
                        it.data?.let { data -> movieAdapter.setData(data) }
                    }
                    is Resource.Loading -> setDataState(Status.LOADING)
                    is Resource.Error -> {
                        setDataState(Status.ERROR)
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        })

    }

    private fun setSearchList() {
        viewModel.movieResult.observe(viewLifecycleOwner, { movies ->
            movieAdapter.setData(movies)
            Log.d(TAG, "data " + movieAdapter.setData(movies))
        })
    }

    private fun setDataState(status: Status) {
        when (status) {
            Status.ERROR -> {
                layMovieBinding.progressBar.visibility = View.GONE
                layMovieBinding.notFound.visibility = View.VISIBLE
                layMovieBinding.notFoundText.visibility = View.VISIBLE
            }
            Status.LOADING -> {
                layMovieBinding.progressBar.visibility = View.VISIBLE
                layMovieBinding.notFound.visibility = View.GONE
                layMovieBinding.notFoundText.visibility = View.GONE
            }
            Status.SUCCESS -> {
                layMovieBinding.progressBar.visibility = View.GONE
                layMovieBinding.notFound.visibility = View.GONE
                layMovieBinding.notFoundText.visibility = View.GONE
            }
        }
    }

    private fun observeSearchQuery() {
        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.setSearchQuery(it)
                }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.searchView.setOnQueryTextListener(null)
        layMovieBinding.rvMovies.adapter = null
        _binding = null
    }

}