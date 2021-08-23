package com.feelme.feelmeapp.features.selectStream.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.MainActivity
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.ActivitySelectStreamBinding
import com.feelme.feelmeapp.features.selectStream.adapter.StreamAdapter
import com.feelme.feelmeapp.features.selectStream.model.StreamItem
import com.google.android.material.button.MaterialButton

class SelectStreamActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectStreamBinding
    private var streamList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectStreamBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val streamingsList = listOf<StreamItem>(
            StreamItem(1,R.drawable.stream_netflix,"stream_netflix"),
            StreamItem(2,R.drawable.stream_prime,"stream_prime"),
            StreamItem(3,R.drawable.stream_disney,"stream_disney"),
            StreamItem(4,R.drawable.stream_apple_tv,"stream_apple_tv"),
            StreamItem(5,R.drawable.stream_google_play,"stream_google_play"),
            StreamItem(6,R.drawable.stream_looke,"stream_looke"),
            StreamItem(8,R.drawable.stream_mubi,"stream_mubi"),
            StreamItem(9,R.drawable.stream_paramount,"stream_paramount"),
            StreamItem(10,R.drawable.stream_hbo_max,"stream_hbo_max"),
            StreamItem(11,R.drawable.stream_crunchyroll,"stream_crunchyroll"),
            StreamItem(12,R.drawable.stream_claro_video,"stream_claro_video"),
            StreamItem(13,R.drawable.stream_telecine,"stream_telecine"),
            StreamItem(14,R.drawable.stream_globoplay,"stream_globoplay"),
            StreamItem(15,R.drawable.stream_appletv_plus,"stream_appletv_plus"),
            StreamItem(16,R.drawable.stream_vix,"stream_vix"),
            StreamItem(17,R.drawable.stream_fox_premium,"stream_fox_premium"),
            StreamItem(18,R.drawable.stream_curiosity,"stream_curiosity"),
            StreamItem(19,R.drawable.stream_kocowa,"stream_kocowa"),
            StreamItem(20,R.drawable.stream_spamflix,"stream_spamflix"),
            StreamItem(21,R.drawable.stream_funimation,"stream_funimation"),
            StreamItem(22,R.drawable.stream_net_movies,"stream_net_movies"),
            StreamItem(23,R.drawable.stream_starzplay,"stream_starzplay"),
            StreamItem(24,R.drawable.stream_docs_ville,"stream_docs_ville"),
            StreamItem(25,R.drawable.stream_watch_argo,"stream_watch_argo"),
            StreamItem(27,R.drawable.stream_magellan,"stream_magellan"),
            StreamItem(28,R.drawable.stream_brodway,"stream_brodway"),
            StreamItem(30,R.drawable.stream_hbo_go,"stream_hbo_go"),
            StreamItem(31,R.drawable.stream_dekkoo,"stream_dekkoo"),
            StreamItem(32,R.drawable.stream_belas_artes,"stream_belas_artes"),
            StreamItem(33,R.drawable.stream_true_story,"stream_true_story"),
            StreamItem(34,R.drawable.stream_da_filmes,"stream_da_filmes"),
            StreamItem(35,R.drawable.stream_korea,"stream_korea"),
            StreamItem(36,R.drawable.stream_hoichoi,"stream_hoichoi"),
            StreamItem(37,R.drawable.stream_old_flix,"stream_old_flix"),
            StreamItem(38,R.drawable.stream_gospel_play,"stream_gospel_play"),
            StreamItem(39,R.drawable.stream_tnt_go,"stream_tnt_go"),
            StreamItem(40,R.drawable.stream_history_play,"stream_history_play"),
            StreamItem(41,R.drawable.stream_now,"stream_now"),
            StreamItem(42,R.drawable.stream_arte_um,"stream_arte_um"),
            StreamItem(43,R.drawable.stream_supo,"stream_supo"),
            StreamItem(44,R.drawable.stream_libreflix,"stream_libreflix"),
            StreamItem(45,R.drawable.stream_tubi,"stream_tubi"),
            StreamItem(46,R.drawable.stream_microsoft_movies,"stream_microsoft_movies"),
            StreamItem(47,R.drawable.stream_filme_filme,"stream_filme_filme"),
            StreamItem(48,R.drawable.stream_kinopop,"stream_kinopop"),
            StreamItem(49,R.drawable.stream_oi_play,"stream_oi_play"),
            StreamItem(50,R.drawable.stream_revry,"stream_revry"),
            StreamItem(51,R.drawable.stream_movie_saints,"stream_movie_saints"),
        )
        binding.rvStreamList.layoutManager = GridLayoutManager(applicationContext, 3, RecyclerView.VERTICAL, false)
        binding.rvStreamList.adapter = StreamAdapter(streamingsList) {
            if(streamList.contains(it.id)) streamList.remove(it.id)
            else streamList.add(it.id)

            if(streamList.count() > 0) {
                binding.btSkipSelectStream.text = "Salvar"
                binding.btSkipSelectStream.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.secondary_color))
                (binding.btSkipSelectStream as MaterialButton).setIconResource(R.drawable.ic_watched_movie)
            } else {
                binding.btSkipSelectStream.text = "Pular"
                binding.btSkipSelectStream.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.primary_color))
                (binding.btSkipSelectStream as MaterialButton).setIconResource(R.drawable.ic_skip)
            }
        }

        binding.btSkipSelectStream.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}