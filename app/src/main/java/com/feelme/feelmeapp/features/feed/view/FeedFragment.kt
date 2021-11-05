package com.feelme.feelmeapp.features.feed.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.adapters.LastMoviesAdapter.LastMoviesAdapter
import com.feelme.feelmeapp.adapters.PagingMovieComments.PagedMovieCommentsAdapter
import com.feelme.feelmeapp.databinding.FragmentFeedBinding
import com.feelme.feelmeapp.features.feed.viewmodel.FeedViewModel
import com.feelme.feelmeapp.features.home.view.HomeFragment
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.features.noInternet.view.NoInternetActivity
import com.feelme.feelmeapp.utils.Command
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : Fragment() {
    private var binding: FragmentFeedBinding? = null
    private val viewModel: FeedViewModel by viewModel()
    private val pagedMovieCommentsAdapter: PagedMovieCommentsAdapter by lazy {
        PagedMovieCommentsAdapter { comment ->
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(HomeFragment.EXTRA_MOVIE_ID, comment.movieId)
            startActivity(intent)
        }
    }

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
        viewModel.getUserFollow()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.onSuccessFriendsStatus.observe(viewLifecycleOwner, {
            binding?.let { FeedFragment ->
                FeedFragment.rvFriendsMovies.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                FeedFragment.rvFriendsMovies.adapter = LastMoviesAdapter(it) {
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(HomeFragment.EXTRA_MOVIE_ID, it.id)
                    startActivity(intent)
                }
            }
            showFeed()
        })

        lifecycleScope.launch {
            viewModel.getFriendsComments().collect { pagingData ->
                pagedMovieCommentsAdapter.submitData(pagingData)
            }
        }

        viewModel.onSuccessFollow.observe(viewLifecycleOwner, {
            if(it.follow.count() == 0) showNoFriends()
        })

        viewModel.command.observe(viewLifecycleOwner, {
            if(it is Command.Error) {
                val intent = Intent(context, NoInternetActivity::class.java)
                startActivity(intent)
            }
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
            it.rvFriendsComments.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = pagedMovieCommentsAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}