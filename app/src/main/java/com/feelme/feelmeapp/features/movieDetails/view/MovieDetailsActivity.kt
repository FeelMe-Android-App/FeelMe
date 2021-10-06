package com.feelme.feelmeapp.features.movieDetails.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
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
import com.feelme.feelmeapp.model.Result
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovie
import com.feelme.feelmeapp.utils.ConstantApp.Emojis.emojiList
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModel()
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

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)

        binding.rvComments.adapter = commentViewPager
        binding.rvComments.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)

        binding.btSave.setOnClickListener {
            val user = Firebase.auth.currentUser
            when(user) {
                is FirebaseUser -> {
                    val movieDetails = viewModel.onSuccessMovieDetails.value
                    movieDetails.let {
                        viewModel.saveUnwatchedMovie(movieId, FeelMeMovie(backdropPath = it?.backdropPath.toString(), title = it?.title ?: ""))
                    }
                }
                else -> {
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
        }

        val emojiList = emojiList.map { MoodList ->
            EmojiList(MoodList.icon, MoodList.name, true) {
                supportFragmentManager.fragments.forEach { Fragment ->
                    (Fragment as DialogFragment).dismiss()
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

        viewModel.command = MutableLiveData()
        viewModel.getMovieDetailsScreen(movieId)
        viewModel.getMovieStatus(movieId)
    }

    override fun onResume() {
        super.onResume()
        setupObservables()
    }

    private fun setupObservables() {
        this.let { MovieDetailsActivity ->
            viewModel.onSuccessMovieDetails.observe(this, { Movie ->
                with(binding) {
                    val runTime = Movie.runtime.getDuration() ?: "--h--min"
                    val yearRelease = Movie.releaseDate.getYear() ?: "----"
                    val released = "$runTime • $yearRelease"
                    Picasso.get().load(Movie.posterPath).placeholder(R.drawable.no_image).into(imgPoster)
                    tvMovieTitle.text = Movie.title
                    tvMovieDescription.text = Movie.overview
                    tvMovieReleaseYear.text = released

                    rvCategories.adapter = MovieCategoriesAdapter(Movie.genreIds) {

                    }
                    rvCategories.isNestedScrollingEnabled = false
                    val layoutManager = FlexboxLayoutManager(applicationContext)
                    layoutManager.flexDirection = FlexDirection.ROW
                    layoutManager.justifyContent = JustifyContent.FLEX_START
                    rvCategories.layoutManager = layoutManager
                }
            })

            viewModel.onSuccessMovieStreaming.observe(this, { Streaming ->
                if(!Streaming.isNullOrEmpty()) {
                    binding.rvStreamings.adapter = MovieStreamingAdapter(Streaming) {

                    }
                    binding.rvStreamings.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
                } else {
                    binding.rvStreamings.isVisible = false
                    binding.tvWatchNow.isVisible = false
                }

                binding.vgLoader.vgLoader.isVisible = false
                binding.vgMovieDetailsFragment.isVisible = true
            })

            viewModel.onSuccessMovieSaved.observe(this, {
//                binding?.btSave.background.setTint(ContextCompat.getColor(applicationContext, R.color.secondary_color))
            })

            viewModel.onSuccessSaveMovie.observe(this, {
                binding.btSave.background.setTint(ContextCompat.getColor(applicationContext, R.color.secondary_color))
            })
        }
    }
}