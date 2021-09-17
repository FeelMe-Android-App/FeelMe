package com.feelme.feelmeapp.features.movieDetails.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.ActivityMovieDetailsBinding
import com.feelme.feelmeapp.extensions.getDuration
import com.feelme.feelmeapp.extensions.getYear
import com.feelme.feelmeapp.features.dialog.usecase.ButtonStyle
import com.feelme.feelmeapp.features.dialog.usecase.DialogData
import com.feelme.feelmeapp.features.dialog.usecase.EmojiList
import com.feelme.feelmeapp.features.dialog.view.Dialog
import com.feelme.feelmeapp.features.home.view.HomeFragment.Companion.EXTRA_MOVIE_ID
import com.feelme.feelmeapp.features.movieDetails.adapter.CommentsAdapter
import com.feelme.feelmeapp.features.movieDetails.adapter.MovieCategoriesAdapter
import com.feelme.feelmeapp.features.movieDetails.adapter.MovieStreamingAdapter
import com.feelme.feelmeapp.features.movieDetails.usecase.Comment
import com.feelme.feelmeapp.features.movieDetails.viewmodel.MovieDetailsViewModel
import com.feelme.feelmeapp.utils.Command
import com.feelme.feelmeapp.utils.ConstantApp.Emojis.emojiList
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            viewModel.getMovieStreaming(movieId)
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

        val emojiList = emojiList.map {
            EmojiList(it.icon, it.name, true) {
                val fragments = supportFragmentManager.fragments;
                fragments.forEach {
                    if(it is DialogFragment) it.dismiss();
                }
            }
        }

        binding.btWatch.setOnClickListener {
            val dialog = Dialog(
                DialogData(
                    title = "Emoji Feeling",
                    subtitle = "O que você sentiu ao assistir esse filme?",
                    image = R.drawable.ic_watched_outlined,
                    emojiList = emojiList
                )
            )
            dialog.isCancelable = false
            dialog.show(this.supportFragmentManager, "LoginDialog")
        }
    }

    private fun setupObservables() {
        this.let { MovieDetailsActivity ->
            viewModel.onSuccessMovieDetails.observe(MovieDetailsActivity, { Movie ->
                with(binding) {
                    val runTime = Movie.runtime.getDuration() ?: "--h--min"
                    val yearRelease = Movie.releaseDate.getYear() ?: "----"
                    val released = "$runTime • $yearRelease"
                    Picasso.get().load(Movie.posterPath).into(imgPoster)
                    tvMovieTitle.text = Movie.title
                    tvMovieDescription.text = Movie.overview
                    tvMovieReleaseYear.text = released

                    rvCategories.adapter = MovieCategoriesAdapter(Movie.genres) {

                    }
                    rvCategories.isNestedScrollingEnabled = false
                    val layoutManager = FlexboxLayoutManager(applicationContext)
                    layoutManager.flexDirection = FlexDirection.ROW
                    layoutManager.justifyContent = JustifyContent.FLEX_START
                    rvCategories.layoutManager = layoutManager
                }
            })

            viewModel.onSuccessMovieStreaming.observe(MovieDetailsActivity, { Streaming ->
                run {
                    if(!Streaming.isNullOrEmpty()) {
                        binding.rvStreamings.adapter = MovieStreamingAdapter(Streaming) {

                        }
                        binding.rvStreamings.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
                    } else {
                        binding.rvStreamings.visibility = View.GONE
                        binding.tvWatchNow.visibility = View.GONE
                    }

                    binding.vgMovieDetailsLoading.visibility = View.GONE
                    binding.vgMovieDetailsFragment.visibility = View.VISIBLE
                }
            })

            viewModel.command.observe(MovieDetailsActivity, {
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