package com.feelme.feelmeapp.features.userProfile.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.CommentMovieItemBinding
import com.feelme.feelmeapp.model.feelmeapi.LastComments
import com.squareup.picasso.Picasso

class LastCommentsAdapter(
    private val lastComments: List<LastComments>,
    private val onClickListener: (comment: LastComments) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CommentMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(lastComments[position], onClickListener)
    }

    override fun getItemCount() = lastComments.count()

    class ViewHolder(
        val binding: CommentMovieItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            comment: LastComments,
            onClickListener: (comment: LastComments) -> Unit
        ) {
            Picasso.get().load(Uri.parse(comment.backdropPath)).placeholder(R.drawable.no_image).into(binding.rivMoviePoster)
            Picasso.get().load(Uri.parse(comment.photoUrl)).placeholder(R.drawable.ic_no_profile_picture).into(binding.ivProfilePicture)
            binding.tvComment.text = comment.comment
            binding.vgComment.setOnClickListener {
                onClickListener(comment)
            }
        }
    }
}