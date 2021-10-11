package com.feelme.feelmeapp.features.profile.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfileAdapter(private val fragmentList: List<Fragment>, val fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount() = fragmentList.count()

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}