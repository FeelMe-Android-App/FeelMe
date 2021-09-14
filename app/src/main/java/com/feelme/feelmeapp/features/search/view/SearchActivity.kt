package com.feelme.feelmeapp.features.search.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.adapters.CategoriasAdapter
import com.feelme.feelmeapp.databinding.ActivitySearchBinding
import com.feelme.feelmeapp.features.home.usecase.Categorias
import java.util.*
import kotlin.concurrent.timerTask

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private var adapterCategorias = CategoriasAdapter()
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timer = Timer()

        binding.tiSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer.cancel()
                timer = Timer()
                timer.schedule(timerTask {
                    Log.i("Key","Typed ${s}")
                }, 1000)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onResume() {
        super.onResume()

        adapterCategorias.setItensList(
            arrayListOf(
                Categorias(
                    1, R.drawable.ic_animation, "Animação"
                ),
                Categorias(
                    2, R.drawable.ic_adventure, "Aventura"
                ),
                Categorias(
                    3, R.drawable.ic_comedy, "Comédia"
                ),
            )
        )
        
        binding.rvCategoria.adapter = adapterCategorias
        binding.rvCategoria.layoutManager =
            LinearLayoutManager(this.applicationContext, RecyclerView.HORIZONTAL, false)


        binding.btBack.setOnClickListener {
            finish()
        }
    }
}