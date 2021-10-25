package com.feelme.feelmeapp.features.feed.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.adapters.LastMoviesAdapter.LastMoviesAdapter
import com.feelme.feelmeapp.databinding.FragmentFeedBinding
import com.feelme.feelmeapp.features.feed.adapter.FriendsMoviesAdapter
import com.feelme.feelmeapp.features.feed.viewmodel.FeedViewModel
import com.feelme.feelmeapp.features.home.usecase.Films
import com.feelme.feelmeapp.firebase.UserProfile
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : Fragment() {
    private var binding: FragmentFeedBinding? = null
    private val viewModel: FeedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            it.fbAddFriend.setOnClickListener {
                findNavController().navigate(R.id.action_feedFragment_to_searchFriendFragment)
            }

            it.btAddFriends.setOnClickListener {
                findNavController().navigate(R.id.action_feedFragment_to_searchFriendFragment)
            }
        }

        viewModel.command = MutableLiveData()
        viewModel.getFriendsStatus()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.onSuccessFriendsStatus.observe(viewLifecycleOwner, {
            binding?.let { FeedFragment ->
                FeedFragment.rvFriendsMovies.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                FeedFragment.rvFriendsMovies.adapter = LastMoviesAdapter(it)
            }
            showFeed()
        })
    }

    private fun showNoFriends() {
        binding?.let {
            it.vgLoader.vgLoader.isVisible = false
            it.vgNoFriends.isVisible = true
            it.vgUserFeed.isVisible = false
        }
    }

    private fun showFeed() {
        binding?.let {
            it.vgLoader.vgLoader.isVisible = false
            it.vgNoFriends.isVisible = false
            it.vgUserFeed.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}