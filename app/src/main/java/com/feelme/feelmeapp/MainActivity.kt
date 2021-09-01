package com.feelme.feelmeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.feelme.feelmeapp.databinding.ActivityMainBinding
import com.feelme.feelmeapp.features.dialog.usecase.DialogData
import com.feelme.feelmeapp.features.dialog.usecase.EmojiList
import com.feelme.feelmeapp.features.dialog.view.Dialog
import com.feelme.feelmeapp.features.search.view.SearchActivity
import com.feelme.feelmeapp.features.whatToWatch.view.WhatToWatchFragment
import moodList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = WhatToWatchFragment()

        val navController = Navigation.findNavController(this, R.id.fragmentNavHost)
        setupWithNavController(binding.bottomNavigationView, navController)
        val emojiList = moodList.map {
            EmojiList(it.icon, it.name) {
                openMoodFragment(it.name)
            }
        }
        binding.bottomNavigationView.menu.getItem(2).setOnMenuItemClickListener {
            Dialog(DialogData(
                title = "O que assistir?",
                subtitle = "Escolha como você quer se sentir após assistir",
                image = R.drawable.ic_watched_outlined,
                emojiList = emojiList
            )).show(this.supportFragmentManager, "CustomDialog")
            true
        }
        binding.bottomNavigationView.menu.getItem(3).setOnMenuItemClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
            true
        }
    }

    private fun openMoodFragment(feeling: String) {
        val arguments = Bundle().apply {
            putString(MOOD_CONST, feeling)
        }

        findNavController(R.id.fragmentNavHost).navigate(R.id.whatToWatchFragment, arguments)
        binding.bottomNavigationView.menu.getItem(2).isChecked = true
    }

    companion object {
        const val MOOD_CONST = "mood"
    }
}