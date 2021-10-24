package com.feelme.feelmeapp.features.userProfile.view

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.ActivityUserProfileBinding
import com.feelme.feelmeapp.features.home.view.HomeFragment
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.features.searchFriend.view.SearchFriendFragment.Companion.USER_ID
import com.feelme.feelmeapp.features.userProfile.adapter.LastCommentsAdapter
import com.feelme.feelmeapp.features.userProfile.adapter.LastWatchedMoviesAdapter
import com.feelme.feelmeapp.features.userProfile.viewmodel.UserProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private val viewModel: UserProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getStringExtra(USER_ID)

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vgProfileHeader.btBack.setOnClickListener {
            finish()
        }

        if(!userId.isNullOrEmpty()) {
            viewModel.command = MutableLiveData()
            viewModel.getUserProfile(userId)
            setupObservables()
        } else {
            finish()
        }
    }

    private fun hideLoader() {
        binding.let {
            it.vgLoader.vgLoader.isVisible = false
            it.vgUserProfile.isVisible = true
        }
    }

    private fun setupObservables() {
        viewModel.onSuccessUserProfile.observe(this, {
            with(binding) {
                vgProfileHeader.tvNomeLogin.text = it.userprofile.name
                vgProfileHeader.includeUserProfile.tvFollowNumber.text = it.userprofile.follow.toString()
                vgProfileHeader.includeUserProfile.tvFollowedNumber.text = it.userprofile.follwed.toString()
                tvStreamings.text = it.userprofile.streaming.toString()
                tvUnWatchedMovies.text = it.userprofile.unwatched.toString()
                tvWacthed.text = it.userprofile.watched.toString()
                hideLoader()
            }
        })

        viewModel.onSuccessLastWatchedMovies.observe(this, {
            with(binding) {
                rvLastWatchedMovies.layoutManager = GridLayoutManager(applicationContext, 3)
                rvLastWatchedMovies.adapter = LastWatchedMoviesAdapter(it) {
                    val intent = Intent(applicationContext, MovieDetailsActivity::class.java)
                    intent.putExtra(HomeFragment.EXTRA_MOVIE_ID, it.id)
                    startActivity(intent)
                }
            }
        })

        viewModel.onSuccessLastComments.observe(this, {
            with(binding) {
                rvLastComments.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                rvLastComments.adapter = LastCommentsAdapter(it) {
                    val intent = Intent(applicationContext, MovieDetailsActivity::class.java)
                    intent.putExtra(HomeFragment.EXTRA_MOVIE_ID, it.movieId)
                    startActivity(intent)
                }
            }
        })
    }
}