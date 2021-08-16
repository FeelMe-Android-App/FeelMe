package com.feelme.feelmeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feelme.feelmeapp.databinding.ActivityOverlayBinding

class OverlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOverlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOverlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabBackAction.setOnClickListener {
            finish()
        }
    }
}