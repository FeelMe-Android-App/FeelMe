package com.feelme.feelmeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feelme.feelmeapp.databinding.ActivityProfileBinding
import com.feelme.feelmeapp.features.profile.view.ProfileFragment

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = ProfileFragment()
        supportFragmentManager.beginTransaction().replace(binding.flProfile.id, fragment).commit()
    }

    override fun onResume() {
        super.onResume()
    }
}