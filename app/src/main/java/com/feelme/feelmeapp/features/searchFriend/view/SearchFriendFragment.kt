package com.feelme.feelmeapp.features.searchFriend.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FragmentSearchFriendBinding

class SearchFriendFragment : Fragment() {
    private var binding: FragmentSearchFriendBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchFriendBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            it.btBack.setOnClickListener {
                findNavController().navigate(R.id.action_searchFriendFragment_to_feedFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}