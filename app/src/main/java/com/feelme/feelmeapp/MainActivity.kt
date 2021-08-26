package com.feelme.feelmeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.feelme.feelmeapp.databinding.ActivityMainBinding
import com.feelme.feelmeapp.features.dialog.usecase.DialogData
import com.feelme.feelmeapp.features.dialog.usecase.EmojiList
import com.feelme.feelmeapp.features.dialog.view.Dialog
import com.feelme.feelmeapp.features.search.view.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = Navigation.findNavController(this, R.id.fragment_navHost)
        setupWithNavController(binding.bottomNavigationView, navController)
        binding.bottomNavigationView.menu.getItem(2).setOnMenuItemClickListener {
            Dialog(DialogData(
                title = "O que assistir?",
                subtitle = "Escolha como você quer se sentir após assistir",
                image = R.drawable.ic_watched_outlined,
                emojiList = listOf(
                    EmojiList(R.drawable.ic_shocked, "Chocado"),
                    EmojiList(R.drawable.ic_afflicted, "Aflito"),
                    EmojiList(R.drawable.ic_sad, "Triste"),
                    EmojiList(R.drawable.ic_impressed, "Impressionado"),
                )
            )).show(this.supportFragmentManager, "CustomDialog")
            true
        }
        binding.bottomNavigationView.menu.getItem(3).setOnMenuItemClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
            true
        }
    }
}