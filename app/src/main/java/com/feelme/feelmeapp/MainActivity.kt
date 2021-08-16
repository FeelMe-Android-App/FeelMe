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

        val facebookLogin = Overlay("Entre", R.drawable.ic_signup, "Faça Login com seu Facebook para acessar esse e outros recursos.", View.TEXT_ALIGNMENT_CENTER)

        val profileUser = Overlay(image = R.drawable.bruna_silva, content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas tincidunt aliquet dui vitae finibus. Nunc gravida dui justo, quis vehicula felis efficitur at. Cras sodales eleifend justo.")

        binding.btOpenOverlay.setOnClickListener {
            val intent = Intent(this, OverlayActivity::class.java)
            with(intent) {
                putExtra(COMPANION_TITLE, facebookLogin.title)
                putExtra(COMPANION_CONTENT, facebookLogin.content)
                putExtra(COMPANION_IMAGE, facebookLogin.image)
                putExtra(COMPANION_ALIGNMENT, facebookLogin.alignment)
            }
            startActivity(intent)
        }

        binding.btOpenProfile.setOnClickListener {
            val intent = Intent(this, OverlayActivity::class.java)
            with(intent) {
                putExtra(COMPANION_TITLE, profileUser.title)
                putExtra(COMPANION_CONTENT, profileUser.content)
                putExtra(COMPANION_IMAGE, profileUser.image)
                putExtra(COMPANION_ALIGNMENT, profileUser.alignment)
            }
            startActivity(intent)
        }
    }
}