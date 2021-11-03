package com.feelme.feelmeapp.features.movieDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.CommentItemBinding
import com.feelme.feelmeapp.features.movieDetails.usecase.Comment
import com.squareup.picasso.Picasso

class CommentsAdapter(
    private val comments: MutableList<Comment>,
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
            Picasso.get().load(comment.image).placeholder(R.drawable.ic_no_profile_picture).into(binding.ivProfilePicture)
            binding.clComment.setOnClickListener {
                onClickListenerProfile(comment)
            }
        }
    }

    fun removeAt(position: Int) {
        comments.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(item: Comment) {
        comments.add(0, item)
        notifyItemRangeInserted(0, comments.count())
    }
}