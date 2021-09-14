package com.feelme.feelmeapp.features.movieDetails.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.ActivityMovieDetailsBinding
import com.feelme.feelmeapp.extensions.getDuration
import com.feelme.feelmeapp.extensions.getYear
import com.feelme.feelmeapp.features.dialog.usecase.ButtonStyle
import com.feelme.feelmeapp.features.dialog.usecase.DialogData
import com.feelme.feelmeapp.features.dialog.view.Dialog
import com.feelme.feelmeapp.features.home.view.HomeFragment.Companion.EXTRA_MOVIE_ID
import com.feelme.feelmeapp.features.movieDetails.adapter.CommentsAdapter
import com.feelme.feelmeapp.features.movieDetails.adapter.MovieCategoriesAdapter
import com.feelme.feelmeapp.features.movieDetails.adapter.MovieStreamingsAdapter
import com.feelme.feelmeapp.features.movieDetails.usecase.Comment
import com.feelme.feelmeapp.features.movieDetails.viewmodel.MovieDetailsViewModel
import com.feelme.feelmeapp.utils.Command
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onResume() {
        super.onResume()

        binding.btBack.setOnClickListener {
            finish()
        }

        val comments = listOf(
            Comment(R.drawable.bruna_silva, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas tincidunt aliquet dui vitae finibus. Nunc gravida dui justo, quis vehicula felis efficitur at. Cras sodales eleifend justo.", 2),
            Comment(R.drawable.bruna_silva, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas tincidunt aliquet dui vitae finibus.", 4),
            Comment(R.drawable.bruna_silva, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas tincidunt aliquet dui vitae finibus.", 4)
        )

        val commentViewPager = CommentsAdapter(comments){
            Dialog(DialogData(content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas tincidunt aliquet dui vitae finibus. Nunc gravida dui justo, quis vehicula felis efficitur at. Cras sodales eleifend justo.", image = R.drawable.bruna_silva)).show(this.supportFragmentManager, "CustomDialog")
        }

        this.let {
            val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
            viewModel = ViewModelProvider(it)[MovieDetailsViewModel::class.java]
            viewModel.command = MutableLiveData()
            viewModel.getMovieById(movieId)
            viewModel.getMovieStreamings(movieId)
            setupObservables()
        }

        binding.rvComments.adapter = commentViewPager
        binding.rvComments.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)

        binding.btSave.setOnClickListener {
            Dialog(
                DialogData(
                    title = "Entre",
                    subtitle = "Faça login com seu Facebook para acessar esse e outros recursos.",
                    image = R.drawable.ic_signup,
                    button = ButtonStyle("Logar com Facebook",R.drawable.ic_facebook,R.color.facebook_bt) {
                        Log.i("ButtonAction","Teste de Ação Personalizada")
                    }
                )
            ).show(this.supportFragmentManager, "LoginDialog")
        }
    }

    private fun setupObservables() {
        this.let {
            viewModel.onSuccessMovieDetails.observe(it, { Movie ->
                with(binding) {
                    Picasso.get().load(Movie.poster_path).into(imgPoster)
                    tvMovieTitle.text = Movie.title
                    tvMovieDescription.text = Movie.overview
                    tvMovieReleaseYear.text = "${Movie.release_date.getYear().toString()} • ${Movie.runtime.getDuration()}"

                    rvCategories.adapter = MovieCategoriesAdapter(Movie.genres) {

                    }
                    rvCategories.isNestedScrollingEnabled = false
                    rvCategories.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
                }
            })

            viewModel.onSuccessMovieStreamings.observe(it, { Flatrate ->
                run {
                    if(!Flatrate.isNullOrEmpty()) {
                        binding.rvStreamings.adapter = MovieStreamingsAdapter(Flatrate) {

                        }
                        binding.rvStreamings.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
                    } else {
                        binding.rvStreamings.visibility = View.GONE
                        binding.tvWatchNow.visibility = View.GONE
                    }
                }
            })

            viewModel.command.observe(it, {
                when(it) {
                    is Command.Loading -> {
                        Log.i("CommandLoading", it.toString())
                    }
                    is Command.Error -> {
                        Log.i("CommandError", it.toString())
                    }
                }
            })
        }
    }
}