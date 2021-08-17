package com.rodrigocordeiro.feelme.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigocordeiro.feelme.R
import com.rodrigocordeiro.feelme.adapter.FriendFeedAdapter
import com.rodrigocordeiro.feelme.adapter.FriendFilmAdapter
import com.rodrigocordeiro.feelme.databinding.FragmentFeedBinding
import com.rodrigocordeiro.feelme.model.FriendsFeed
import com.rodrigocordeiro.feelme.model.FriendsFilm

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

        val film1 = FriendsFilm(
            film = R.drawable.avengerspnp
        )

        val film2 = FriendsFilm(
            film = R.drawable.black_summer
        )

        val film3 = FriendsFilm(
            film = R.drawable.greys_anatomy
        )

        val film4 = FriendsFilm(
            film = R.drawable.the_silence
        )

        val film5 = FriendsFilm(
            film = R.drawable.she_ra
        )

        val feed1 = FriendsFeed(
            friendPicture = R.drawable.natalia,
            friendFeed = "rrrrrrrr",
            feedFilm = R.drawable.avengerspnp
        )

        val feed2 = FriendsFeed(
            friendPicture = R.drawable.gezierri,
            friendFeed = "xxxxxx",
            feedFilm = R.drawable.black_summer
        )

        val feed3 = FriendsFeed(
            friendPicture = R.drawable.paulo,
            friendFeed = "xxxxxx",
            feedFilm = R.drawable.the_silence
        )

        val filmList = listOf(film1, film2, film3, film4, film5)

        val friendFilmAdapter = FriendFilmAdapter(
            friendFilmList = filmList
        )

        val feedList = listOf(feed1, feed2, feed3)

        val friendFeedAdapter = FriendFeedAdapter(
            friendFeedList = feedList
        )

        binding?.let {
            with(it) {

                rvFriendview.layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                rvFriendview.adapter = friendFilmAdapter

                rvFeedview.layoutManager =
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)

                rvFeedview.adapter = friendFeedAdapter
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}