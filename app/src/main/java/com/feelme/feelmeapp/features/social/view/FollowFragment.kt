package com.feelme.feelmeapp.features.social.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.adapters.PagingFriendsAdapter.PagedFriendsAdapter
import com.feelme.feelmeapp.databinding.FragmentFollowBinding
import com.feelme.feelmeapp.features.searchFriend.view.SearchFriendFragment.Companion.USER_ID
import com.feelme.feelmeapp.features.social.viewmodel.SocialViewModel
import com.feelme.feelmeapp.features.userProfile.view.UserProfileActivity
import kotlinx.coroutines.launch

class FollowFragment : Fragment() {
    private var binding: FragmentFollowBinding? = null
    private val viewModel: SocialViewModel by viewModel()
    private val pagedFollowAdapter: PagedFriendsAdapter by lazy{
        PagedFriendsAdapter{ friend ->
            val intent = Intent(context, UserProfileActivity::class.java)
            intent.putExtra(USER_ID, friend.userId)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.command = MutableLiveData()
        setupObservables()

    }

    private fun setupObservables() {
        lifecycleScope.launch {
            viewModel.followUser().collect { pagingData ->
                pagedFollowAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}