package com.feelme.feelmeapp.adapters.UserStreamListAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.StreamItemBinding
import com.squareup.picasso.Picasso

class UserStreamListAdapter(
    private val streamList: List<UserStreamListData>,
    private val onLongClickListener: (stream: UserStreamListData) -> Unit
): RecyclerView.Adapter<UserStreamListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StreamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(streamList[position], onLongClickListener)
    }

    override fun getItemCount() = streamList.count()

    class ViewHolder(val binding: StreamItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            stream: UserStreamListData,
            onLongClickListener: (stream: UserStreamListData) -> Unit
        ) {
            Picasso.get().load(stream.logoPath).placeholder(R.drawable.stream_not_image).into(binding.ivStreamerLogo)

        }
    }
}