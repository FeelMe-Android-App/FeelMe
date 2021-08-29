package com.feelme.feelmeapp.features.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.adapters.CategoriasAdapter
import com.feelme.feelmeapp.adapters.EmAltaAdapter
import com.feelme.feelmeapp.databinding.FragmentHomeBinding
import com.feelme.feelmeapp.features.home.usecase.Categorias
import com.feelme.feelmeapp.features.home.usecase.Filmes
import com.feelme.feelmeapp.features.home.usecase.HomeUseCase
import com.feelme.feelmeapp.features.home.viewmodel.HomeViewModel
import com.feelme.feelmeapp.model.NowPlaying
import com.feelme.feelmeapp.utils.Command
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var adapterCategorias = CategoriasAdapter()

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

        setCategorias()
        setRecyclerViewCategorias()
    }

    private fun setupObservables() {
        activity?.let { nowPlaying ->
            viewModel.onSuccessNowPlaying.observe(nowPlaying, {
                binding.rvEmAlta.adapter = EmAltaAdapter(it) {
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(EXTRA_MOVIE_ID, it.id)
                    startActivity(intent)
                }
                binding.rvEmAlta.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                binding.rvEmAlta.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            })
        }
    }

    private fun setRecyclerViewCategorias() {
        binding.rvCategoria.adapter = adapterCategorias
        binding.rvCategoria.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    private fun setCategorias() {
        adapterCategorias.setItensList(
            arrayListOf(
                Categorias(
                    1, R.drawable.ic_animation, "Animação"
                ),
                Categorias(
                    2, R.drawable.ic_adventure, "Aventura"
                ),
                Categorias(
                    3, R.drawable.ic_comedy, "Comédia"
                ),
            )
        )
    }

    companion object {
        const val EXTRA_MOVIE_ID = "movieId"
    }
}