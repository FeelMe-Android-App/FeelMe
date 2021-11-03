package com.feelme.feelmeapp.features.social.adapter

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SocialAdapter(
    private val socialList: List<Fragment>,
    val social: FragmentActivity): FragmentStateAdapter(social) {
    override fun getItemCount() = socialList.count()

    override fun createFragment(position: Int): Fragment = socialList[position]
}