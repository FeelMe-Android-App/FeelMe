package com.feelme.feelmeapp.features.comments.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FragmentCommentsBinding

class CommentsFragment : Fragment() {
    var binding: FragmentCommentsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}