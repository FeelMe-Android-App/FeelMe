package com.rodrigocordeiro.feelme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigocordeiro.feelme.databinding.FriendfilmItemBinding
import com.rodrigocordeiro.feelme.model.FriendsFilm

class FriendFilmAdapter(
    private val friendFilmList: List<FriendsFilm>
) : RecyclerView.Adapter<FriendFilmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = FriendfilmItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(friendFilmList[position])

    }

    override fun getItemCount() = friendFilmList.size



    class ViewHolder(
        val binding: FriendfilmItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            friendsFilm: FriendsFilm
        ) {
            binding.ivFriendFilmItem.setImageResource(friendsFilm.film)

        }

    }

}



