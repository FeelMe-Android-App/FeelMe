package com.feelme.feelmeapp.features.search.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.adapters.CategoriesAdapter
import com.feelme.feelmeapp.databinding.ActivitySearchBinding
import com.feelme.feelmeapp.features.home.usecase.Categories
import java.util.*
import kotlin.concurrent.timerTask

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private var adapterCategories = CategoriesAdapter()
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

        adapterCategories.setItensList(
            arrayListOf(
                Categories(
                    1, R.drawable.ic_animation, "Animação"
                ),
                Categories(
                    2, R.drawable.ic_adventure, "Aventura"
                ),
                Categories(
                    3, R.drawable.ic_comedy, "Comédia"
                ),
            )
        )
        
        binding.rvCategoria.adapter = adapterCategories
        binding.rvCategoria.layoutManager =
            LinearLayoutManager(this.applicationContext, RecyclerView.HORIZONTAL, false)


        binding.btBack.setOnClickListener {
            finish()
        }
    }
}