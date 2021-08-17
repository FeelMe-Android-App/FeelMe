package com.feelme.feelmeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.feelme.feelmeapp.databinding.ActivityMainBinding
import com.feelme.feelmeapp.features.overlay.model.Overlay
import com.feelme.feelmeapp.features.overlay.view.OverlayActivity
import com.feelme.feelmeapp.features.overlay.view.OverlayActivity.Companion.COMPANION_ALIGNMENT
import com.feelme.feelmeapp.features.overlay.view.OverlayActivity.Companion.COMPANION_CONTENT
import com.feelme.feelmeapp.features.overlay.view.OverlayActivity.Companion.COMPANION_IMAGE
import com.feelme.feelmeapp.features.overlay.view.OverlayActivity.Companion.COMPANION_TITLE

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}