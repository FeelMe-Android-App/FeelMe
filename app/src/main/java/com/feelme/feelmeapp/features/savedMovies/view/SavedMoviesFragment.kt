package com.feelme.feelmeapp.features.savedMovies.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.feelme.feelmeapp.databinding.FragmentSavedMoviesBinding
import com.feelme.feelmeapp.features.savedMovies.adapter.MyMoviesListAdapter
import com.feelme.feelmeapp.features.savedMovies.viewmodel.SavedMoviesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedMoviesFragment : Fragment() {
    private var binding: FragmentSavedMoviesBinding? = null
    private val viewModel: SavedMoviesViewModel by viewModel()
    private val myMovieListAdapter: MyMoviesListAdapter by lazy {
        MyMoviesListAdapter { movie->

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedMoviesBinding.inflate(inflater, container, false)
        setupObservables()
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.let {
            setupRecyclerView()
        }
    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getList().collect { pagingData ->
                myMovieListAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.let {
            it.rvMovieList.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = myMovieListAdapter
            }
        }
    }
}