package com.feelme.feelmeapp.features.profile.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FragmentProfileBinding
import com.feelme.feelmeapp.features.profile.adapter.ProfileAdapter
import com.feelme.feelmeapp.features.savedMovies.view.SavedMoviesFragment
import com.feelme.feelmeapp.firebase.UserProfile
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btBack.setOnClickListener {
            activity?.onBackPressed()
        }

        setupObservables()

        val fragments = listOf(SavedMoviesFragment())
        val icons = listOf(R.drawable.ic_save_film)
        val profilePagerAdapter = ProfileAdapter(fragments, this)

        binding.let {
            it.vpUserProfile.adapter = profilePagerAdapter
            TabLayoutMediator(it.tlUserProfile, it.vpUserProfile) { tab, position ->
                tab.icon = ResourcesCompat.getDrawable(resources, icons[position], null)
            }.attach()
        }
    }

    private fun setupObservables() {
        activity?.let {
            UserProfile.currentUser.observe(viewLifecycleOwner, { CurrentUser ->
                CurrentUser?.let {
                    Picasso.get().load(it.photoUrl).placeholder(R.drawable.ic_no_profile_picture).into(binding.ivFotoLogin)
                    binding.tvNomeLogin.text = "Olá, ${it.displayName}"
                }
            })
        }
    }
}