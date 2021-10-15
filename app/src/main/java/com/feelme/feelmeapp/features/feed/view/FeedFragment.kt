package com.feelme.feelmeapp.features.feed.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FragmentFeedBinding
import com.feelme.feelmeapp.features.feed.adapter.FriendsMoviesAdapter
import com.feelme.feelmeapp.features.home.usecase.Films

class FeedFragment : Fragment() {
    private var binding: FragmentFeedBinding? = null

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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}