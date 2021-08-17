package com.rodrigocordeiro.feelme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigocordeiro.feelme.databinding.FriendfeedItemBinding
import com.rodrigocordeiro.feelme.model.FriendsFeed

class FriendFeedAdapter(
    private val friendFeedList: List<FriendsFeed>
) : RecyclerView.Adapter<FriendFeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = FriendfeedItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(friendFeedList[position])
    }

    override fun getItemCount() = friendFeedList.size

    class ViewHolder(
        val binding: FriendfeedItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            friendsFeed: FriendsFeed
        ) {
            binding.ivFriendFilmFeedItem.setImageResource(friendsFeed.feedFilm)
            binding.civFeed.setImageResource(friendsFeed.friendPicture)
            binding.tietFriendFeed.text

        }
    }

}