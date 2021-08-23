package com.feelme.feelmeapp.features.movieDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.CommentItemBinding
import com.feelme.feelmeapp.features.movieDetails.model.Comment

class CommentsAdapter(
    private val comments: List<Comment>,
    private val onClickListenerProfile: (comment: Comment) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(comments[position], onClickListenerProfile)
    }

    override fun getItemCount() = comments.count()

    class ViewHolder(
        val binding: CommentItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            comment: Comment,
            onClickListenerProfile: (comment: Comment) -> Unit
        ) {
            binding.tvComment.text = comment.comment
            binding.ivProfilePicture.setImageResource(comment.image)
            binding.clComment.setOnClickListener {
                onClickListenerProfile(comment)
            }
        }
    }
}