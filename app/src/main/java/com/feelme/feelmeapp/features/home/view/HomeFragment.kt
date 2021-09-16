package com.feelme.feelmeapp.features.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.ProfileActivity
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.adapters.CategoriesAdapter
import com.feelme.feelmeapp.adapters.EmAltaAdapter
import com.feelme.feelmeapp.databinding.FragmentHomeBinding
import com.feelme.feelmeapp.features.home.usecase.Categories
import com.feelme.feelmeapp.features.home.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var adapterCategories = CategoriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            viewModel = ViewModelProvider(it)[HomeViewModel::class.java]
            viewModel.command = MutableLiveData()
            viewModel.getNowPlayingMovies()
            setupObservables()
        }

        binding.ivFotoLogin.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }

        setCategories()
        setRecyclerViewCategories()
    }

    private fun setupObservables() {
        activity?.let { nowPlaying ->
            viewModel.onSuccessNowPlaying.observe(nowPlaying, {
                binding.rvEmAlta.adapter = EmAltaAdapter(it) { Result ->
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(EXTRA_MOVIE_ID, Result.id)
                    startActivity(intent)
                }
                binding.rvEmAlta.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                binding.rvEmAlta.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                binding.vgHomeLoading.visibility = View.GONE
                binding.vgHomeFragment.visibility = View.VISIBLE
            })
        }
    }

    private fun setRecyclerViewCategories() {
        binding.rvCategoria.adapter = adapterCategories
        binding.rvCategoria.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    private fun setCategories() {
        adapterCategories.setItensList(
            arrayListOf(
                Categories(
                    1, R.drawable.ic_animation, "Animação"
                ),
                Categories(
                    2, R.drawable.ic_adventure, "Aventura"
                ),
                Categories(
                    3, R.drawable.ic_comedy, "Comédia"
                ),
            )
        )
    }

    companion object {
        const val EXTRA_MOVIE_ID = "movieId"
    }
}