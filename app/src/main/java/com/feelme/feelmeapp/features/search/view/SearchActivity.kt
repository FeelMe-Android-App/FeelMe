package com.feelme.feelmeapp.features.search.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.feelme.feelmeapp.adapters.PagingMovieGridAdapter.PagedMovieGridAdapter
import com.feelme.feelmeapp.databinding.ActivitySearchBinding
import com.feelme.feelmeapp.features.home.view.HomeFragment
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.features.noInternet.view.NoInternetActivity
import com.feelme.feelmeapp.features.search.viewmodel.SearchViewModel
import com.feelme.feelmeapp.utils.Command
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.timerTask

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var timer: Timer
    private val viewModel: SearchViewModel by viewModel()
    private val pagedMovieGridAdapter: PagedMovieGridAdapter by lazy {
        PagedMovieGridAdapter { movie ->
            val intent = Intent(applicationContext, MovieDetailsActivity::class.java)
            intent.putExtra(HomeFragment.EXTRA_MOVIE_ID, movie.movieId)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timer = Timer()
        viewModel.command = MutableLiveData<Command>()

        binding.tiSearch.requestFocus()
        binding.rvMovies.layoutManager = GridLayoutManager(applicationContext, 3)
        binding.rvMovies.adapter = pagedMovieGridAdapter

        binding.btBack.setOnClickListener {
            finish()
        }

        binding.tiSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard(this@SearchActivity)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()) {
                    binding.searchLoading.isVisible = false
                    binding.rvMovies.isVisible = false
                } else {
                    pagedMovieGridAdapter.submitData(lifecycle, PagingData.empty())
                    binding.searchLoading.isVisible = true
                    binding.rvMovies.isVisible = false
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(timerTask {
                        viewModel.recyclerViewReloaded()
                        lifecycleScope.launch {
                            val initialLoad = viewModel.onSuccessSearch.value == "refreshed"
                            viewModel.getSearchMovies(newText, initialLoad).collectLatest { pagingData ->
                                pagedMovieGridAdapter.submitData(pagingData)
                            }
                        }
                    }, 1000)
                }
                return true
            }
        })
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.onSuccessSearch.observe(this, {
            if(it == "refreshed") {
                binding.rvMovies.layoutManager = GridLayoutManager(applicationContext, 3)
                binding.searchLoading.isVisible = true
                binding.rvMovies.isVisible = false
            } else {
                binding.rvMovies.layoutManager = GridLayoutManager(applicationContext, 3)
                binding.searchLoading.isVisible = false
                binding.rvMovies.isVisible = true
            }
        })

        viewModel.command.observe(this, {
            if(it is Command.Error) {
                val intent = Intent(applicationContext, NoInternetActivity::class.java)
                startActivity(intent)
            }
        })
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if no view has focus
        val currentFocusedView = activity.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            currentFocusedView.clearFocus()
        }
    }
}