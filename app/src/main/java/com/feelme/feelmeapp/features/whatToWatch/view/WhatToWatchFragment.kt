package com.feelme.feelmeapp.features.whatToWatch.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.MainActivity
import com.feelme.feelmeapp.MainActivity.Companion.MOOD_CONST
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FragmentWhatToWatchBinding
import com.feelme.feelmeapp.features.home.view.HomeFragment.Companion.EXTRA_MOVIE_ID
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.features.whatToWatch.adapter.MoviesMoodListAdapter
import com.feelme.feelmeapp.features.whatToWatch.viewmodel.WhatToWatchViewModel
import com.feelme.feelmeapp.utils.ConstantApp.Emojis.emojiList

class WhatToWatchFragment : Fragment() {
    private lateinit var viewModel: WhatToWatchViewModel
    private lateinit var binding: FragmentWhatToWatchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWhatToWatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(arguments) {
            val mood = this?.getString(MOOD_CONST, "")
            if (mood != null) {
                val moodData = emojiList.find {
                    it.name == mood
                }

                moodData?.let { MoodList ->
                    val title = "${context?.getString(R.string.top_ten)} ${moodData.name}"
                    binding.ivEmojiFeeling.setImageResource(MoodList.icon)
                    binding.tvEmojiFeeling.text = MoodList.name
                    binding.tvTopTeenFeeling.text = title
                    binding.btBack.setOnClickListener {
                        (activity as MainActivity).restartMood()
                    }

                    this@WhatToWatchFragment.let {
                        viewModel = ViewModelProvider(it)[WhatToWatchViewModel::class.java]
                        viewModel.command = MutableLiveData()
                        viewModel.getDiscoverMovies(genres = moodData.categories.joinToString(separator = ","))
                        setupObservables()
                    }
                }
            }
        }
    }

    private fun setupObservables() {
        this.let {
            viewModel.onSuccessWhatToWatch.observe(viewLifecycleOwner, { Results ->
                binding.tvMoviesMoodList.adapter = MoviesMoodListAdapter(Results) {
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(EXTRA_MOVIE_ID, it.id)
                    startActivity(intent)
                }
                binding.tvMoviesMoodList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                binding.vgWhatToWatchLoading.visibility = View.GONE
                binding.vgWhatToWatch.visibility = View.VISIBLE
            })
        }
    }
}