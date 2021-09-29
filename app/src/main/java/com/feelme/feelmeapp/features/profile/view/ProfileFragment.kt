package com.feelme.feelmeapp.features.profile.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FragmentProfileBinding
import com.feelme.feelmeapp.firebase.UserProfile
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