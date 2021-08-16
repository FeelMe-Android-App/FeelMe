package com.feelme.feelmeapp.features.overlay.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.ActivityOverlayBinding

class OverlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOverlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOverlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(COMPANION_TITLE)
        val content = intent.getStringExtra(COMPANION_CONTENT)
        val alignment = intent.getIntExtra(COMPANION_ALIGNMENT, View.TEXT_ALIGNMENT_TEXT_START)
        val imagem = intent.getIntExtra(COMPANION_IMAGE, R.drawable.ic_signup)

        if(content.isNullOrEmpty()) finish()



        with(binding) {
            if(title.isNullOrEmpty()) binding.tvTitle.visibility = View.GONE
            else tvTitle.text = title

            ivDestaqImage.setImageResource(imagem)
            tvContent.text = content
            tvContent.textAlignment = alignment
        }

        binding.fabBackAction.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val COMPANION_TITLE = "title"
        const val COMPANION_CONTENT = "content"
        const val COMPANION_IMAGE = "image"
        const val COMPANION_ACTION = "action"
        const val COMPANION_ALIGNMENT = "alignment"
    }
}