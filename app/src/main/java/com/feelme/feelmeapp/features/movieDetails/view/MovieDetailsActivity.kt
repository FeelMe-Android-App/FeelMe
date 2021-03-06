package com.feelme.feelmeapp.features.movieDetails.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
import com.feelme.feelmeapp.features.movieDetails.adapter.CommentsAdapter
import com.feelme.feelmeapp.features.movieDetails.adapter.MovieCategoriesAdapter
import com.feelme.feelmeapp.features.movieDetails.adapter.MovieStreamingAdapter
import com.feelme.feelmeapp.features.movieDetails.adapter.SwipeToDeleteCallback
import com.feelme.feelmeapp.features.movieDetails.usecase.Comment
import com.feelme.feelmeapp.features.movieDetails.viewmodel.MovieDetailsViewModel
import com.feelme.feelmeapp.features.searchFriend.view.SearchFriendFragment
import com.feelme.feelmeapp.features.userProfile.view.UserProfileActivity
import com.feelme.feelmeapp.globalLiveData.UserMoviesList
import com.feelme.feelmeapp.globalLiveData.UserProfile
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovie
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovieComment
import com.feelme.feelmeapp.utils.Command
import com.feelme.feelmeapp.utils.ConstantApp.Emojis.emojiList
import com.feelme.feelmeapp.utils.TakeScreenShotAndShare
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
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
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

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

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) shareScreenShot()
            else Snackbar.make(binding.btShare, "Permiss??o n??o concedida", Snackbar.LENGTH_LONG).show()
        }

        viewModel.command = MutableLiveData()
        viewModel.getMovieDetailsScreen(movieId)
        binding.btShare.setOnClickListener { getScreenShot() }
        Picasso.get().load(UserProfile.currentUser.value?.photoUrl.toString()).into(binding.ivFotoLogin)
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
                    val released = "$runTime ??? $yearRelease"
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
                if(CommentsList.count() > 0) setupCommentsRecyclerView(CommentsList)
                setupCommentsToDelete(CommentsList)
            })

            UserProfile.currentUser.observe(this, {
                if(it?.logged == true) {
                    loggedScreen()
                }
            })

            viewModel.onSuccessPostedComments.observe(this, {
                val newComment = Comment(
                    Uri.parse(it.photoUrl),
                    it.comment,
                    it.uid,
                    it._id
                )

                val commentsList = if(this::commentsAdapter.isInitialized) commentsAdapter.getList() else mutableListOf()

                if(commentsList.isNullOrEmpty()) setupCommentsRecyclerView(mutableListOf(newComment))
                else commentsAdapter.addItem(newComment)

                binding.etUserComment.text = null
                setupCommentsToDelete(commentsList)
            })

            viewModel.command.observe(this, {
                if(it is Command.Error) {
                    binding.vgNoInternet.vgNoInternet.isVisible = true
                    binding.vgLoader.vgLoader.isVisible = false
                }
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
                subtitle = "O que voc?? sentiu ao assistir esse filme?",
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
                subtitle = "Fa??a login com seu Facebook para acessar esse e outros recursos.",
                image = R.drawable.ic_signup,
                button = ButtonStyle("Logar com Facebook",R.drawable.ic_facebook,R.color.facebook_bt) {
                    Log.i("ButtonAction","Teste de A????o Personalizada")
                }
            )
        ).show(this.supportFragmentManager, "LoginDialog")
    }

    private fun setupCommentsRecyclerView(commentsList: List<Comment>) {
        commentsAdapter = CommentsAdapter(commentsList.toMutableList()) {
            val intent = Intent(applicationContext, UserProfileActivity::class.java)
            intent.putExtra(SearchFriendFragment.USER_ID, it.profileId)
            startActivity(intent)
        }

        binding.rvComments.isVisible = true
        binding.tvFriendsComments.isVisible = true
        binding.rvComments.adapter = commentsAdapter
        binding.rvComments.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
    }

    private fun setupCommentsToDelete(commentsList: List<Comment>) {
        val userCommentPosition: MutableList<Int> = mutableListOf()
        commentsList.forEachIndexed { index, comment -> if(comment.profileId == UserProfile.currentUser.value?.uid) userCommentPosition.add(index) }

        val swipeHandler = object : SwipeToDeleteCallback(context = applicationContext, userCommentPosition) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(userComments.contains(viewHolder.absoluteAdapterPosition)) {
                    val adapter = binding.rvComments.adapter as CommentsAdapter
                    val commentId = adapter.getPosition(viewHolder.absoluteAdapterPosition)
                    viewModel.deleteComment(commentId)
                    adapter.removeAt(viewHolder.absoluteAdapterPosition)

                    if(adapter.itemCount == 0) {
                        binding.rvComments.isVisible = false
                        binding.tvFriendsComments.isVisible = false
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvComments)
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) shareScreenShot()
        else {
            when {
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    shareScreenShot()
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }

    fun shareScreenShot() {
        val view = findViewById<ConstraintLayout>(R.id.vgMovieDetailsScreen)
        val screenShot = TakeScreenShotAndShare(applicationContext, view, view.findViewById<TextView>(R.id.tvMovieDescription).bottom + 40, view.width)
        val uri: Uri? = screenShot.getScreenShotUri()
        if(uri is Uri) {
            val intent = Intent(Intent.ACTION_SEND).setType("image/*")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(intent)
        }
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