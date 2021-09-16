package com.feelme.feelmeapp.features.feed.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val moviesList = mutableListOf(
            Films(1, "The Revenant", "Jan 31,2015", R.drawable.the_revenant),
            Films(2, "No country for old men", "Mar 02,2007", R.drawable.no_country_for_old_men),
            Films(3, "The Fight Clube", "Nov 09,1999", R.drawable.the_fight_club),
            Films(4, "There will be blood", "Aug 17,2007", R.drawable.there_will_be_blood),
            Films(5, "Trainspotting", "Fev 11,1996", R.drawable.trainspotting),
            Films(6, "Tene", "Dec 24,2020", R.drawable.tene),
        )

        binding?.rvMoviesList?.adapter = FriendsMoviesAdapter(moviesList)
        binding?.rvMoviesList?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}