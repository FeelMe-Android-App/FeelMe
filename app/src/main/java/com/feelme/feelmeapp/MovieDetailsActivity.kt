package com.feelme.feelmeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feelme.feelmeapp.databinding.ActivityMovieDetailsBinding
import com.feelme.feelmeapp.databinding.ActivitySplashBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}