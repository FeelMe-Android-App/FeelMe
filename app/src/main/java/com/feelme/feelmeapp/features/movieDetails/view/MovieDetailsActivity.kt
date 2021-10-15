package com.feelme.feelmeapp.features.movieDetails.view

import android.content.Intent
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
import com.feelme.feelmeapp.features.genre.view.GenreActivity
import com.feelme.feelmeapp.features.home.view.HomeFragment
import com.feelme.feelmeapp.features.home.view.HomeFragment.Companion.EXTRA_MOVIE_ID
import com.feelme.feelmeapp.features.movieDetails.adapter.CommentsAdapter
import com.feelme.feelmeapp.features.movieDetails.adapter.MovieCategoriesAdapter
import com.feelme.feelmeapp.features.movieDetails.adapter.MovieStreamingAdapter
import com.feelme.feelmeapp.features.movieDetails.usecase.Comment
import com.feelme.feelmeapp.features.movieDetails.viewmodel.MovieDetailsViewModel
import com.feelme.feelmeapp.firebase.UserProfile
import com.feelme.feelmeapp.model.Result
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovie
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovieComment
import com.feelme.feelmeapp.utils.ConstantApp.Emojis.emojiList
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import okhttp3.internal.toImmutableList
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModel()
    private lateinit var binding: ActivityMovieDetailsBinding
    private var movieSaved: Boolean = false
    private var movieWatched: Boolean = false
    private val userProfile = UserProfile.currentUser.value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btBack.setOnClickListener {
            finish()
        }

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)

        binding.btSave.setOnClickListener {
            if(movieSaved) {
                removeMovie(movieId)
            } else {
                val user = Firebase.auth.currentUser
                when(user) {
                    is FirebaseUser -> {
                        val movieDetails = viewModel.onSuccessMovieDetails.value
                        movieDetails.let {
                            binding.btSave.background.setTint(ContextCompat.getColor(applicationContext, R.color.secondary_color))
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
        }

        val emojiList = emojiList.map { MoodList ->
            EmojiList(MoodList.icon, MoodList.name, true) {
                binding.btWatch.background.setTint(ContextCompat.getColor(applicationContext, R.color.secondary_color))
                binding.btSave.background.setTint(ContextCompat.getColor(applicationContext, R.color.clean_primary_color))

                supportFragmentManager.fragments.forEach { Fragment ->
                    (Fragment as DialogFragment).dismiss()
                }
            }
        }

        binding.btWatch.setOnClickListener {
            val movieDetails = viewModel.onSuccessMovieDetails.value

            if(movieWatched) {
                removeMovie(movieId)
            } else {
                movieDetails?.let {
                    viewModel.saveWatchedMovie(movieId, FeelMeMovie(backdropPath = it.backdropPath.toString(), title = it.title ?: ""))
                }

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

        binding.vgMovieDetails.viewTreeObserver.addOnScrollChangedListener {
            if(binding.vgMovieDetails.getChildAt(0).bottom <= binding.vgMovieDetails.height + (binding.vgMovieDetails.scrollY + 150)) {
                binding.btWatch.isVisible = false
                binding.btSave.isVisible = false
            } else {
                binding.btWatch.isVisible = true
                binding.btSave.isVisible = true
            }
        }

        binding.btPostComment.setOnClickListener {
            val text = binding.etUserComment.text
            val comments = mutableListOf(Comment(
                UserProfile.currentUser.value?.photoUrl,
                text.toString(),
                UserProfile.currentUser.value?.uid ?: ""
            ))
            viewModel.onSuccessMovieComments.value?.let {
                comments.addAll((it))
            }

            val commentsAdapter = CommentsAdapter(comments.toImmutableList()) {

            }
            binding.rvComments.adapter = commentsAdapter

            viewModel.saveComment(movieId, FeelMeMovieComment(text.toString(), viewModel.onSuccessMovieDetails.value?.backdropPath ?: ""))
        }

        viewModel.command = MutableLiveData()
        viewModel.getMovieDetailsScreen(movieId)
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
                        val intent = Intent(applicationContext, GenreActivity::class.java)
                        intent.putExtra(HomeFragment.EXTRA_CATEGORY_ID, it.id)
                        intent.putExtra(HomeFragment.EXTRA_CATEGORY_NAME, it.name)
                        startActivity(intent)
                    }
                    rvCategories.isNestedScrollingEnabled = false
                    val layoutManager = FlexboxLayoutManager(applicationContext)
                    layoutManager.flexDirection = FlexDirection.ROW
                    layoutManager.justifyContent = JustifyContent.FLEX_START
                    rvCategories.layoutManager = layoutManager
                }
            })

            viewModel.onSuccessMovieStreaming.observe(this, { Streaming ->
                if(Streaming.isNullOrEmpty()) {
                    binding.rvStreamings.isVisible = false
                    binding.tvWatchNow.isVisible = false
                } else {
                    binding.rvStreamings.adapter = MovieStreamingAdapter(Streaming) {

                    }
                    binding.rvStreamings.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
                }

                binding.vgLoader.vgLoader.isVisible = false
                binding.vgMovieDetailsFragment.isVisible = true
            })

            viewModel.onSuccessMovieSaved.observe(this, {
                binding.btSave.background.setTint(ContextCompat.getColor(applicationContext, R.color.secondary_color))
                movieSaved = true
            })

            viewModel.onSuccessMovieWatched.observe(this, {
                binding.btWatch.background.setTint(ContextCompat.getColor(applicationContext, R.color.secondary_color))
                movieWatched = true
            })

            viewModel.onSuccessMovieComments.observe(this, {
                val commentsAdapter = CommentsAdapter(it) {

                }

                binding.rvComments.adapter = commentsAdapter
                binding.rvComments.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            })
        }
    }

    private fun removeMovie(movieId: Int) {
        binding.btWatch.background.setTint(ContextCompat.getColor(applicationContext, R.color.clean_primary_color))
        binding.btSave.background.setTint(ContextCompat.getColor(applicationContext, R.color.clean_primary_color))
        viewModel.removeMovie(movieId)
    }

    companion object {
        const val MOVIE_ID = "movieId"
        const val MOVIE_BACKDROP_PATH = "movieBackdropPath"
        const val MOVIE_TITLE = "movieTitle"
        const val FEELING_ID = "feelingId"
        const val COMMENT = "comment"
    }
}