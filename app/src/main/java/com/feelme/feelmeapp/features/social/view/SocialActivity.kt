package com.feelme.feelmeapp.features.social.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.ActivitySocialBinding
import com.feelme.feelmeapp.features.searchFriend.view.SearchFriendFragment
import com.feelme.feelmeapp.features.searchFriend.view.SearchFriendFragment.Companion.USER_ID
import com.feelme.feelmeapp.features.social.adapter.SocialAdapter
import com.feelme.feelmeapp.firebase.UserProfile
import com.google.android.material.tabs.TabLayoutMediator

class SocialActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySocialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra(USER_ID)
        UserProfile.currentUser.value?.follow?.let {
            it
            it.find { it.uid === userId }
        }

    }

    fun setupFragment(){
        val fragments = listOf(FollowingFragment(), FollowFragment())
        val titles = listOf("Seguindo", "Seguidores")
        val socialPagerAdapter = SocialAdapter(fragments,this)

        binding.let {
            it.vpSocial.adapter = socialPagerAdapter
            TabLayoutMediator(it.tlSocialTabs, it.vpSocial){ tab, position ->
                tab.text = titles[position]
            }.attach()
        }
    }


}