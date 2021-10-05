package com.feelme.feelmeapp.features.watchedMovies.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.adapters.PagingSquareAdapter.PagedSquareImagesAdapter
import com.feelme.feelmeapp.databinding.FragmentWatchedMoviesBinding
import com.feelme.feelmeapp.features.home.view.HomeFragment
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.features.savedMovies.viewmodel.SavedMoviesViewModel
import com.feelme.feelmeapp.features.watchedMovies.viewmodel.WatchedMoviesModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WatchedMoviesFragment : Fragment() {
    private var binding: FragmentWatchedMoviesBinding? = null
    private val viewModel: WatchedMoviesModel by viewModel()
    private val pagedSquareImagesAdapter: PagedSquareImagesAdapter by lazy {
        PagedSquareImagesAdapter { movie ->
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(HomeFragment.EXTRA_MOVIE_ID, movie.movieId)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchedMoviesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservables()
        setupRecyclerView()
    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getWatchedMoviesList().collect { pagingData ->
                pagedSquareImagesAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.let {
            it.rvMovieList.adapter = pagedSquareImagesAdapter
            it.rvMovieList.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}