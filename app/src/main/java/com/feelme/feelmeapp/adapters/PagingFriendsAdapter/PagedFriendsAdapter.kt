package com.feelme.feelmeapp.adapters.PagingFriendsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.FriendSearchItemBinding
import com.squareup.picasso.Picasso

class PagedFriendsAdapter(
    private val onClickListenerFriend: (friend: PagedFriendsModel) -> Unit
) : PagingDataAdapter<PagedFriendsModel, PagedFriendsAdapter.ViewHolder>(PagedFriendsModel.DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListenerFriend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FriendSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(
        val binding: FriendSearchItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            friend: PagedFriendsModel?,
            onClickListenerFriend: (friend: PagedFriendsModel) -> Unit
        ) {
            friend?.let {
                Picasso.get().load(friend.profilePicture).placeholder(R.drawable.ic_no_profile_picture).into(binding.tvUserProfilePicture)
                binding.tvUserName.text = friend.name
                binding.vgUserItem.setOnClickListener { onClickListenerFriend(friend) }
            }
        }
    }
}