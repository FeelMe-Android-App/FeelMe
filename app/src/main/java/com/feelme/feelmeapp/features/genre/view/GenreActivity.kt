package com.feelme.feelmeapp.features.genre.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.feelme.feelmeapp.adapters.PagingMovieGridAdapter.PagedMovieGridAdapter
import com.feelme.feelmeapp.databinding.ActivityGenreBinding
import com.feelme.feelmeapp.features.genre.viewmodel.GenreViewModel
import com.feelme.feelmeapp.features.home.view.HomeFragment
import com.feelme.feelmeapp.features.home.view.HomeFragment.Companion.EXTRA_CATEGORY_ID
import com.feelme.feelmeapp.features.home.view.HomeFragment.Companion.EXTRA_CATEGORY_NAME
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.features.noInternet.view.NoInternetActivity
import com.feelme.feelmeapp.globalLiveData.UserStreamings
import com.feelme.feelmeapp.utils.Command
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGenreBinding
    private var genreId: Int? = null
    private val viewModel: GenreViewModel by viewModel()
    private val pagedMovieGridAdapter: PagedMovieGridAdapter by lazy {
        PagedMovieGridAdapter { movie ->
            val intent = Intent(applicationContext, MovieDetailsActivity::class.java)
            intent.putExtra(HomeFragment.EXTRA_MOVIE_ID, movie.movieId)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.let {
            genreId = intent.getIntExtra(EXTRA_CATEGORY_ID, 0)
            val genreName = intent.getStringExtra(EXTRA_CATEGORY_NAME)

            binding.tvGenreName.text = genreName
            binding.btBack.setOnClickListener {
                finish()
            }

            viewModel.command = MutableLiveData()
            setupObservables()
            setupRecyclerView()
        }
    }

    private fun setupObservables() {
        lifecycleScope.launch {
            genreId?.let {
                val streamings = UserStreamings.getUserStreamingsServices()
                viewModel.getMoviesByGenre(streamings, it).collect { pagingData ->
                    pagedMovieGridAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvGenreMovies.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = pagedMovieGridAdapter
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}