package com.feelme.feelmeapp.features.profile.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FragmentProfileBinding
import com.feelme.feelmeapp.features.comments.view.CommentsFragment
import com.feelme.feelmeapp.features.profile.adapter.ProfileAdapter
import com.feelme.feelmeapp.features.profile.viewmodel.ProfileViewModel
import com.feelme.feelmeapp.features.savedMovies.view.SavedMoviesFragment
import com.feelme.feelmeapp.features.streamingServices.view.StreamingServicesFragment
import com.feelme.feelmeapp.features.watchedMovies.view.WatchedMoviesFragment
import com.feelme.feelmeapp.firebase.UserProfile
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.vgProfileHeader?.btBack?.setOnClickListener {
            activity?.onBackPressed()
        }

        viewModel.command = MutableLiveData()
        viewModel.getMyProfile()
        setupObservables()
        setupFragment()
    }

    private fun setupObservables() {
        activity?.let {
            UserProfile.currentUser.observe(viewLifecycleOwner, { CurrentUser ->
                CurrentUser?.let {
                    Picasso.get().load(it.photoUrl).placeholder(R.drawable.ic_no_profile_picture).into(binding?.vgProfileHeader?.includeUserProfile?.ivFotoLogin)
                    binding?.vgProfileHeader?.tvNomeLogin?.text = "Olá, ${it.displayName}"
                }
            })

            viewModel.onSuccessProfile.observe(viewLifecycleOwner, { UserProfileData ->
                binding?.let {
                    it.vgProfileHeader.includeUserProfile.tvFollowNumber.text = UserProfileData.followCount.toString()
                    it.vgProfileHeader.includeUserProfile.tvFollowedNumber.text = UserProfileData.followedCount.toString()
                    it.vgLoader.vgLoader.isVisible = false
                    it.vgProfile.isVisible = true
                }
            })
        }
    }

    fun setupFragment() {
        val fragments = listOf(SavedMoviesFragment(), WatchedMoviesFragment(), StreamingServicesFragment())
        val titles = listOf("Salvos", "Assistidos", "Streamings")
        val icons = listOf(R.drawable.ic_saved, R.drawable.ic_watched_movie, R.drawable.ic_streamings)
        val profilePagerAdapter = ProfileAdapter(fragments, this)

        binding?.let {
            it.vpUserProfile.adapter = profilePagerAdapter
            TabLayoutMediator(it.tlUserProfile, it.vpUserProfile) { tab, position ->
                tab.text = titles[position]
                tab.icon = ResourcesCompat.getDrawable(resources, icons[position], null)
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}