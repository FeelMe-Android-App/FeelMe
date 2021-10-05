package com.feelme.feelmeapp.features.streamingServices.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FragmentStreamingServicesBinding

class StreamingServicesFragment : Fragment() {
    var binding: FragmentStreamingServicesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreamingServicesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}