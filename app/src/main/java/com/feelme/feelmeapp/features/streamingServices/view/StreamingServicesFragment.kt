package com.feelme.feelmeapp.features.streamingServices.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FragmentStreamingServicesBinding
import com.feelme.feelmeapp.features.selectStream.adapter.StreamAdapter
import com.feelme.feelmeapp.features.streamingServices.viewmodel.StreamingServicesViewModel
import com.feelme.feelmeapp.model.StreamDetails
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
            val streamList = mutableListOf<StreamDetails>()
            it.forEach {
                streamList.add(
                    StreamDetails(
                        providerId = it.stream.providerId,
                        displayPriority = it.stream.displayPriority,
                        logoPath = it.stream.logoPath,
                        providerName = it.stream.providerName,
                        selected = false
                    )
                )
            }

            binding?.let {
                it.rvStreamingServices.layoutManager = GridLayoutManager(context, 3)
                it.rvStreamingServices.adapter = StreamAdapter(streamList.toList()) {

                }
                hideLoader()
            }
        })
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
}