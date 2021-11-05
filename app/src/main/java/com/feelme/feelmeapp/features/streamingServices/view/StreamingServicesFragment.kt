package com.feelme.feelmeapp.features.streamingServices.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.feelme.feelmeapp.adapters.UserStreamListAdapter.UserStreamListAdapter
import com.feelme.feelmeapp.adapters.UserStreamListAdapter.UserStreamListData
import com.feelme.feelmeapp.databinding.FragmentStreamingServicesBinding
import com.feelme.feelmeapp.features.streamingServices.viewmodel.StreamingServicesViewModel
import com.feelme.feelmeapp.globalLiveData.UserStreamings
import org.koin.androidx.viewmodel.ext.android.viewModel

class StreamingServicesFragment : Fragment() {
    var binding: FragmentStreamingServicesBinding? = null
    val viewModel: StreamingServicesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreamingServicesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.command = MutableLiveData()
        viewModel.getMyStreamingServices()
        setupObservables()
    }

    fun setupObservables() {
        viewModel.onSuccessStreamingServices.observe(viewLifecycleOwner, {
            val streamList = mutableListOf<UserStreamListData>()
            val streamIds = mutableListOf<Int>()
            it.forEach {
                streamList.add(
                    UserStreamListData(
                        logoPath = it.stream.logoPath,
                        providerId = it.stream.providerId
                    )
                )
                streamIds.add(it.stream.providerId)
            }

            UserStreamings.setUserStreamings(streamIds)

            binding?.let {
                it.rvStreamingServices.layoutManager = GridLayoutManager(context, 3)
                it.rvStreamingServices.adapter = UserStreamListAdapter(streamList.toList()) {

                }
                hideLoader()
            }
        })

        viewModel.noStreamingServices.observe(viewLifecycleOwner, {
            emptyList()
        })
    }

    private fun emptyList() {
        binding?.let {
            it.vgLoader.vgLoader.isVisible = false
            it.vgNoStreaming.isVisible = true
            it.rvStreamingServices.isVisible = false
        }
    }

    private fun hideLoader() {
        binding?.let {
            it.vgLoader.vgLoader.isVisible = false
            it.vgNoStreaming.isVisible = false
            it.rvStreamingServices.isVisible = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val STREAM_LIST = "stream_list"
    }
}