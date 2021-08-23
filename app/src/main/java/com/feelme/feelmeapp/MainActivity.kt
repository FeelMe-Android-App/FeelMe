package com.feelme.feelmeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.feelme.feelmeapp.databinding.ActivityMainBinding
import com.feelme.feelmeapp.features.dialog.model.DialogData
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
            Dialog(DialogData(title = "O que assistir?",subtitle = "Escolha como você quer se sentir após assistir",image = R.drawable.ic_watched_outlined)).show(this.supportFragmentManager, "CustomDialog")
            true
        }
        binding.bottomNavigationView.menu.getItem(3).setOnMenuItemClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
            true
        }
    }
}