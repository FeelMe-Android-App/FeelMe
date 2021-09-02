package com.feelme.feelmeapp.features.whatToWatch.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feelme.feelmeapp.MainActivity.Companion.MOOD_CONST
import com.feelme.feelmeapp.databinding.FragmentWhatToWatchBinding
import com.feelme.feelmeapp.utils.ConstantApp.emojis.emojiList

class WhatToWatchFragment : Fragment() {
    private lateinit var binding: FragmentWhatToWatchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhatToWatchBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
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

                if (moodData != null) {
                    binding.ivEmojiFeeling.setImageResource(moodData.icon)
                    binding.tvEmojiFeeling.text = moodData.name
                    binding.tvTopTeenFeeling.text = "#10 Filmes para ficar ${moodData.name}"
                }
            }
        }
    }
}