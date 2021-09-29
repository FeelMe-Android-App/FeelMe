package com.feelme.feelmeapp.features.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.ProfileActivity
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.adapters.CategoriesAdapter
import com.feelme.feelmeapp.adapters.EmAltaAdapter
import com.feelme.feelmeapp.databinding.FragmentHomeBinding
import com.feelme.feelmeapp.features.genre.view.GenreActivity
import com.feelme.feelmeapp.features.home.viewmodel.HomeViewModel
import com.feelme.feelmeapp.firebase.UserProfile
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

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
            viewModel.command = MutableLiveData()
            viewModel.getGenres()
            viewModel.getNowPlayingMovies()
            setupObservables()
        }

        binding.ivFotoLogin.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }
    }

    private fun setupObservables() {
        activity?.let { FragmentActivity ->
            viewModel.onSuccessNowPlaying.observe(FragmentActivity, {
                binding.rvEmAlta.adapter = EmAltaAdapter(it) { Result ->
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(EXTRA_MOVIE_ID, Result.id)
                    startActivity(intent)
                }
                binding.rvEmAlta.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                binding.rvEmAlta.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                binding.vgHomeLoading.isVisible = false
                binding.vgHomeFragment.isVisible = true
            })

            viewModel.onSucessGenres.observe(FragmentActivity, { GenreList ->
                binding.rvCategoria.adapter = CategoriesAdapter(GenreList) {
                    val intent = Intent(context, GenreActivity::class.java)
                    intent.putExtra(EXTRA_CATEGORY_ID, it.id)
                    intent.putExtra(EXTRA_CATEGORY_NAME, it.name)
                    startActivity(intent)
                }
                binding.rvCategoria.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            })

            UserProfile.currentUser.observe(FragmentActivity, { CurrentUser ->
                CurrentUser?.let {
                    val homeText = "Olá, ${it.displayName}"
                    Picasso.get().load(it.photoUrl).placeholder(R.drawable.ic_no_profile_picture).into(binding.ivFotoLogin)
                    binding.tvNomeLogin.text = homeText
                }
            })
        }
    }

    companion object {
        const val EXTRA_MOVIE_ID = "movieId"
        const val EXTRA_CATEGORY_ID = "categoryId"
        const val EXTRA_CATEGORY_NAME = "categoryName"
    }
}