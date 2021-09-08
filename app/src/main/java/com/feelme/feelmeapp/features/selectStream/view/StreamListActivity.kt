package com.feelme.feelmeapp.features.selectStream.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.MainActivity
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.ActivitySelectStreamBinding
import com.feelme.feelmeapp.features.selectStream.adapter.StreamAdapter
import com.feelme.feelmeapp.features.selectStream.viewmodel.StreamListViewModel
import com.feelme.feelmeapp.utils.Command
import com.google.android.material.button.MaterialButton

class StreamListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectStreamBinding
    private var streamList = mutableListOf<Int>()
    private lateinit var viewModel: StreamListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectStreamBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        this.let {
            viewModel = ViewModelProvider(this)[StreamListViewModel::class.java]
            viewModel.command = MutableLiveData()
            viewModel.getStreamList()
            setupObservables()

        }

        binding.btSkipSelectStream.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setupObservables() {

        this.let {
            viewModel.onSuccessStreamList.observe(it, {
                binding.rvStreamList.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                binding.rvStreamList.recycledViewPool.setMaxRecycledViews(0,0)
                binding.rvStreamList.layoutManager = GridLayoutManager(applicationContext, 3, RecyclerView.VERTICAL, false)
                binding.rvStreamList.adapter = StreamAdapter(it) {
                    if(streamList.contains(it.provider_id)) streamList.remove(it.provider_id)
                    else streamList.add(it.provider_id)

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
            })

            viewModel.command.observe(it, {
                when(it) {
                    it as Command.Loading -> {

                    }
                }
            })
        }
    }
}