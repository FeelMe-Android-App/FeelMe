package com.feelme.feelmeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feelme.feelmeapp.databinding.ActivityNoInternetBinding

class NoInternetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoInternetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}