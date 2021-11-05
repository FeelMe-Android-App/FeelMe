package com.feelme.feelmeapp.features.userProfile.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.ActivityUserProfileBinding
import com.feelme.feelmeapp.features.home.view.HomeFragment
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.features.searchFriend.view.SearchFriendFragment.Companion.USER_ID
import com.feelme.feelmeapp.features.userProfile.adapter.LastCommentsAdapter
import com.feelme.feelmeapp.features.userProfile.adapter.LastWatchedMoviesAdapter
import com.feelme.feelmeapp.features.userProfile.viewmodel.UserProfileViewModel
import com.feelme.feelmeapp.globalLiveData.UserProfile
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private val viewModel: UserProfileViewModel by viewModel()
    private var isFollowed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getStringExtra(USER_ID)
        UserProfile.currentUser.value?.follow?.let {
            it
            it.find { it.uid === userId }
        }

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vgProfileHeader.btBack.setOnClickListener {
            finish()
        }

        binding.vgProfileHeader.toolUserProfile.menu.clear()

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

    private fun setupBtUnfollow(uid: String, followers: Int) {
        with(binding) {
            btFollow.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.secondary_color)
            btFollow.text = resources.getString(R.string.followed)
            btFollow.setOnClickListener {
                if(isFollowed == true) {
                    isFollowed = false
                    val newFollowers = followers - 1
                    vgProfileHeader.includeUserProfile.tvFollowedNumber.text = newFollowers.toString()
                    btFollow.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.white_ten_percent)
                    btFollow.text = resources.getString(R.string.follow)
                    viewModel.unfollowUser(uid)
                }
            }
        }
    }

    private fun setupBtFollow(uid: String, followers: Int) {
        with(binding) {
            btFollow.setOnClickListener {
                if(isFollowed == false) {
                    isFollowed = true
                    val newFollowers = followers + 1
                    vgProfileHeader.includeUserProfile.tvFollowedNumber.text = newFollowers.toString()
                    btFollow.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.secondary_color)
                    btFollow.text = resources.getString(R.string.followed)
                    viewModel.followUser(uid)
                }
            }
        }
    }

    private fun setupObservables() {
        viewModel.onSuccessUserProfile.observe(this, {
            with(binding) {
                vgProfileHeader.tvNomeLogin.text = it.userprofile.name
                vgProfileHeader.includeUserProfile.tvFollowNumber.text = it.userprofile.follow.toString()
                vgProfileHeader.includeUserProfile.tvFollowedNumber.text = it.userprofile.followed.toString()
                tvStreamings.text = it.userprofile.streaming.toString()
                tvUnWatchedMovies.text = it.userprofile.unwatched.toString()
                tvWacthed.text = it.userprofile.watched.toString()
                isFollowed = it.userprofile.isfollowed
                Picasso.get().load(it.userprofile.photoUrl).placeholder(R.drawable.ic_no_profile_picture).into(vgProfileHeader.includeUserProfile.ivFotoLogin)
                if(it.userprofile.isfollowed) setupBtUnfollow(it.userprofile.uid, it.userprofile.followed)
                else setupBtFollow(it.userprofile.uid, it.userprofile.followed)
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