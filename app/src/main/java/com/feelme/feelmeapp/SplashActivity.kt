package com.feelme.feelmeapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.feelme.feelmeapp.databinding.ActivitySplashBinding
import com.feelme.feelmeapp.features.selectStream.view.StreamListActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timer().schedule(object: TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, StreamListActivity::class.java))
                finish()
            }
        }, 3500)
    }
}