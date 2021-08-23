package com.feelme.feelmeapp.features.movieDetails.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.ActivityMovieDetailsBinding
import com.feelme.feelmeapp.databinding.ActivitySplashBinding
import com.feelme.feelmeapp.features.dialog.model.ButtonStyle
import com.feelme.feelmeapp.features.dialog.model.DialogData
import com.feelme.feelmeapp.features.dialog.view.Dialog
import com.feelme.feelmeapp.features.movieDetails.adapter.CommentsAdapter
import com.feelme.feelmeapp.features.movieDetails.model.Comment

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onResume() {
        super.onResume()

        binding.btBack.setOnClickListener {
            finish()
        }

        val comments = listOf(
            Comment(R.drawable.bruna_silva, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas tincidunt aliquet dui vitae finibus. Nunc gravida dui justo, quis vehicula felis efficitur at. Cras sodales eleifend justo.", 2),
            Comment(R.drawable.bruna_silva, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas tincidunt aliquet dui vitae finibus.", 4),
            Comment(R.drawable.bruna_silva, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas tincidunt aliquet dui vitae finibus.", 4)
        )

        val commentViewPager = CommentsAdapter(comments){
            Dialog(DialogData(content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas tincidunt aliquet dui vitae finibus. Nunc gravida dui justo, quis vehicula felis efficitur at. Cras sodales eleifend justo.", image = R.drawable.bruna_silva)).show(this.supportFragmentManager, "CustomDialog")
        }

        binding.rvComments.adapter = commentViewPager
        binding.rvComments.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)

        binding.btSave.setOnClickListener {
            Dialog(
                DialogData(
                    title = "Entre",
                    subtitle = "Faça login com seu Facebook para acessar esse e outros recursos.",
                    image = R.drawable.ic_signup,
                    button = ButtonStyle("Logar com Facebook",R.drawable.ic_facebook,R.color.facebook_bt) {
                        Log.i("ButtonAction","Teste de Ação Personalizada")
                    }
                )
            ).show(this.supportFragmentManager, "LoginDialog")
        }
    }
}