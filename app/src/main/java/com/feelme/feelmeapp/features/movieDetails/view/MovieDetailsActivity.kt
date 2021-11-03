package com.feelme.feelmeapp.features.movieDetails.view

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
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
import com.feelme.feelmeapp.features.movieDetails.adapter.*
import com.feelme.feelmeapp.features.movieDetails.usecase.Comment
import com.feelme.feelmeapp.features.movieDetails.viewmodel.MovieDetailsViewModel
import com.feelme.feelmeapp.features.searchFriend.view.SearchFriendFragment
import com.feelme.feelmeapp.features.userProfile.view.UserProfileActivity
import com.feelme.feelmeapp.globalLiveData.UserMoviesList
import com.feelme.feelmeapp.globalLiveData.UserProfile
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovie
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovieComment
import com.feelme.feelmeapp.utils.ConstantApp.Emojis.emojiList
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.properties.Delegates

class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModel()
    private lateinit var binding: ActivityMovieDetailsBinding
    private var movieId by Delegates.notNull<Int>()
    private var movieSaved: Boolean = false
    private var movieWatched: Boolean = false
    private val userProfile = UserProfile.currentUser.value
    private val userMovieListEvents = UserMoviesList
    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btBack.setOnClickListener {
            finish()
        }

        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)

        if (userProfile?.logged == true) {
            loggedScreen()
        } else {
            anonymousScreen()
        }

        viewModel.command = MutableLiveData()
        viewModel.getMovieDetailsScreen(movieId)
        binding.btShare.setOnClickListener { getScreenShot() }
        setupObservables()
    }

    private fun anonymousScreen() {
        binding.apply {
            rvComments.isVisible = false
            tvFriendsComments.isVisible = false
            etComment.isVisible = false
            btSave.setOnClickListener { showLoginFacebookDialog() }
            btWatch.setOnClickListener { showLoginFacebookDialog() }
        }
    }

    private fun loggedScreen() {
        binding.apply {
            rvComments.isVisible = false
            etComment.isVisible = true
            tvFriendsComments.isVisible = false

            setupSaveButton()
            setupWatchButton()

            btPostComment.setOnClickListener {
                val text = binding.etUserComment.text
                viewModel.saveComment(movieId, FeelMeMovieComment(text.toString(), viewModel.onSuccessMovieDetails.value?.backdropPath ?: ""))
            }
        }
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

            viewModel.onSuccessMovieComments.observe(this, { CommentsList ->
                commentsAdapter = CommentsAdapter(CommentsList.toMutableList()) {
                    val intent = Intent(applicationContext, UserProfileActivity::class.java)
                    intent.putExtra(SearchFriendFragment.USER_ID, it.profileId)
                    startActivity(intent)
                }

                val userCommentPosition: MutableList<Int> = mutableListOf()
                CommentsList.forEachIndexed { index, comment -> if(comment.profileId == UserProfile.currentUser.value?.uid) userCommentPosition.add(index) }

                val swipeHandler = object : SwipeToDeleteCallback(context = applicationContext, userCommentPosition) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        if(userComments.contains(viewHolder.absoluteAdapterPosition)) {
                            val adapter = binding.rvComments.adapter as CommentsAdapter
                            val commentId = CommentsList[viewHolder.absoluteAdapterPosition]._id
                            viewModel.deleteComment(commentId)
                            adapter.removeAt(viewHolder.absoluteAdapterPosition)
                        }
                    }
                }
                val itemTouchHelper = ItemTouchHelper(swipeHandler)
                itemTouchHelper.attachToRecyclerView(binding.rvComments)

                if(CommentsList.count() > 0) {
                    binding.rvComments.isVisible = true
                    binding.tvFriendsComments.isVisible = true
                    binding.rvComments.adapter = commentsAdapter
                    binding.rvComments.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                }
            })

            UserProfile.currentUser.observe(this, {
                if(it?.logged == true) {
                    loggedScreen()
                }
            })

            viewModel.onSuccessPostedComments.observe(this, {
                commentsAdapter.addItem(Comment(
                    Uri.parse(it.uid.photoUrl),
                    it.comment,
                    it.uid.uid,
                    it._id
                ))
            })
        }
    }

    private fun showEmojiFeelingDialog() {
        val emojiList = emojiList.map { MoodList ->
            EmojiList(MoodList.icon, MoodList.name, true) {
                binding.btWatch.background.setTint(ContextCompat.getColor(applicationContext, R.color.secondary_color))
                binding.btSave.background.setTint(ContextCompat.getColor(applicationContext, R.color.clean_primary_color))

                movieSaved = false
                movieWatched = true
                saveWatchedMovie()

                supportFragmentManager.fragments.forEach { Fragment ->
                    (Fragment as DialogFragment).dismiss()
                }
            }
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

    private fun showLoginFacebookDialog() {
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

    private fun setupSaveButton() {
        binding.btSave.setOnClickListener {
            userMovieListEvents.emitUnwatchedMovieHasChanged(true)
            if(movieSaved && !movieWatched) {
                movieSaved = false
                removeMovie(movieId)
            }
            else if(!movieSaved && !movieWatched) {
                saveUnWatchedMovie()
            }
        }
    }

    private fun setupWatchButton() {
        binding.btWatch.setOnClickListener {
            userMovieListEvents.emitWatchedMovieHasChanged(true)
            if(movieWatched) {
                movieWatched = false
                movieSaved = false
                removeMovie(movieId)
            }
            else showEmojiFeelingDialog()
        }
    }

    private fun saveWatchedMovie() {
        val movie = viewModel.onSuccessMovieDetails.value
        movie?.let {
            viewModel.saveWatchedMovie(it.id, FeelMeMovie(
                it.posterPath ?: "",
                title = it.title
            ))
        }
    }

    private fun saveUnWatchedMovie() {
        binding.btWatch.background.setTint(ContextCompat.getColor(applicationContext, R.color.clean_primary_color))
        binding.btSave.background.setTint(ContextCompat.getColor(applicationContext, R.color.secondary_color))

        val movie = viewModel.onSuccessMovieDetails.value
        movie?.let {
            viewModel.saveUnwatchedMovie(it.id, FeelMeMovie(
                it.posterPath ?: "",
                title = it.title
            ))
        }
    }

    private fun removeMovie(movieId: Int) {
        binding.btWatch.background.setTint(ContextCompat.getColor(applicationContext, R.color.clean_primary_color))
        binding.btSave.background.setTint(ContextCompat.getColor(applicationContext, R.color.clean_primary_color))
        viewModel.removeMovie(movieId)
    }

    fun getScreenShot() {
        val view = this.window.decorView.rootView
        val intent = Intent(Intent.ACTION_SEND).setType("image/*")
        val returnedBitmat = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmat)
        val bgDrawable = view.background
        if(bgDrawable != null) bgDrawable.draw(canvas)
        else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        val bytes = ByteArrayOutputStream()
        returnedBitmat.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        var fos: OutputStream?
        val filename = "screenshot.jpg"
        var uri: Uri?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            applicationContext.contentResolver.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                uri = imageUri
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            uri = image.toUri()
            fos = FileOutputStream(image)
        }

        fos?.use {
            returnedBitmat.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(intent)
    }

    companion object {
        const val MOVIE_ID = "movieId"
        const val MOVIE_BACKDROP_PATH = "movieBackdropPath"
        const val MOVIE_TITLE = "movieTitle"
        const val FEELING_ID = "feelingId"
        const val COMMENT = "comment"
        const val COMMENT_ID = "commentId"
    }
}