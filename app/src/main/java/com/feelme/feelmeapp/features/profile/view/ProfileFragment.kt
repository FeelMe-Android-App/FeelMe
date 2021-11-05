package com.feelme.feelmeapp.features.profile.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.feelme.feelmeapp.MainActivity
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FragmentProfileBinding
import com.feelme.feelmeapp.features.noInternet.view.NoInternetActivity
import com.feelme.feelmeapp.features.profile.adapter.ProfileAdapter
import com.feelme.feelmeapp.features.profile.viewmodel.ProfileViewModel
import com.feelme.feelmeapp.features.savedMovies.view.SavedMoviesFragment
import com.feelme.feelmeapp.features.selectStream.view.StreamListActivity
import com.feelme.feelmeapp.features.streamingServices.view.StreamingServicesFragment
import com.feelme.feelmeapp.features.watchedMovies.view.WatchedMoviesFragment
import com.feelme.feelmeapp.globalLiveData.UserProfile
import com.feelme.feelmeapp.globalLiveData.UserStreamings
import com.feelme.feelmeapp.utils.Command
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

            UserProfile.currentUser.observe(viewLifecycleOwner, { UserProfile ->
                if(UserProfile?.logged == false) startActivity(Intent(context, MainActivity::class.java))
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

            it.tlUserProfile.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    it.fbEditStream.isVisible = tab?.position == 2
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            it.fbEditStream.setOnClickListener {
                val intent = Intent(context, StreamListActivity::class.java)
                intent.putExtra(StreamingServicesFragment.STREAM_LIST, ArrayList(UserStreamings.getUserStreamings.value ?: mutableListOf()))
                startActivity(intent)
            }

            it.vgProfileHeader.toolUserProfile.setOnMenuItemClickListener { MenuItem ->
                when(MenuItem.itemId) {
                    R.id.itLogout -> {
                        Firebase.auth.signOut()
                        UserProfile.logOut()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}