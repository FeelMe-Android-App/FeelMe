package com.feelme.feelmeapp.features.selectStream.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.MainActivity
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.ActivitySelectStreamBinding
import com.feelme.feelmeapp.features.noInternet.view.NoInternetActivity
import com.feelme.feelmeapp.features.selectStream.adapter.StreamAdapter
import com.feelme.feelmeapp.features.selectStream.viewmodel.StreamListViewModel
import com.feelme.feelmeapp.features.streamingServices.view.StreamingServicesFragment.Companion.STREAM_LIST
import com.feelme.feelmeapp.globalLiveData.UserStreamings
import com.feelme.feelmeapp.modeldb.UserStreamList
import com.feelme.feelmeapp.utils.Command
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class StreamListActivity() : AppCompatActivity() {
    private lateinit var binding: ActivitySelectStreamBinding
    private var streamList = mutableListOf<Int>()
    private val viewModel: StreamListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectStreamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val streamListSelectedIntent = intent.getIntegerArrayListExtra(STREAM_LIST)
        if(!streamListSelectedIntent.isNullOrEmpty()) streamList = streamListSelectedIntent
    }

    override fun onResume() {
        super.onResume()

        this.let {
            viewModel.command = MutableLiveData()
            viewModel.getMyStreamListFromDb()
            viewModel.getStreamList()
            setupObservables()
        }

        binding.btSkipSelectStream.setOnClickListener {
            val stream = streamList.map {
                UserStreamList(it)
            }
            viewModel.saveMyStreamListDb(stream)
            UserStreamings.setUserStreamings(streamList)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setupObservables() {
        viewModel.onSuccessStreamList.observe(this, { StreamDetailsList ->
            binding.rvStreamList.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            binding.rvStreamList.recycledViewPool.setMaxRecycledViews(0,0)
            binding.rvStreamList.layoutManager = GridLayoutManager(applicationContext, 3, RecyclerView.VERTICAL, false)

            if(streamList.isNullOrEmpty()) {
                binding.rvStreamList.adapter = StreamAdapter(StreamDetailsList) { StreamDetails ->
                    if(streamList.contains(StreamDetails.providerId)) streamList.remove(StreamDetails.providerId)
                    else streamList.add(StreamDetails.providerId)

                    if(streamList.count() > 0) {
                        binding.btSkipSelectStream.text = applicationContext.getString(R.string.save)
                        binding.btSkipSelectStream.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.secondary_color))
                        (binding.btSkipSelectStream as MaterialButton).setIconResource(R.drawable.ic_watched_movie)
                    } else {
                        binding.btSkipSelectStream.text = applicationContext.getString(R.string.skip)
                        binding.btSkipSelectStream.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.primary_color))
                        (binding.btSkipSelectStream as MaterialButton).setIconResource(R.drawable.ic_skip)
                    }
                }
            } else {
                binding.btSkipSelectStream.text = applicationContext.getString(R.string.save)
                binding.rvStreamList.adapter = StreamAdapter(StreamDetailsList, ArrayList(streamList)) { StreamDetails ->
                    if(streamList.contains(StreamDetails.providerId)) streamList.remove(StreamDetails.providerId)
                    else streamList.add(StreamDetails.providerId)
                }
            }

            binding.vgLoader.vgLoader.visibility = View.GONE
            binding.vgSelectStream.visibility = View.VISIBLE
        })

        viewModel.onSuccessUserStreamList.observe(this, {
            if(!it.isNullOrEmpty() && streamList.count() == 0) {
                val streamingIds = it.map {
                    it.streamId
                }.toMutableList()

                UserStreamings.setUserStreamings(streamingIds)

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

        viewModel.command.observe(this, {
            if(it is Command.Error) {
                val intent = Intent(applicationContext, NoInternetActivity::class.java)
                startActivity(intent)
            }
        })
    }
}