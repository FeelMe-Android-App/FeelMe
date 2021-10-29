package com.feelme.feelmeapp.adapters.PagingMovieComments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.databinding.CommentMovieItemBinding
import com.squareup.picasso.Picasso

class PagedMovieCommentsAdapter(
    private val onClickListenerComment: (comment: PagedMovieCommentsModel) -> Unit
): PagingDataAdapter<PagedMovieCommentsModel, PagedMovieCommentsAdapter.ViewHolder>(PagedMovieCommentsModel.DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListenerComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CommentMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(
        val binding: CommentMovieItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            comment: PagedMovieCommentsModel?,
            onClickListenerComment: (comment: PagedMovieCommentsModel) -> Unit
        ) {
            comment?.let {
                binding.tvComment.text = comment.comment
                Picasso.get().load(comment.profilePhoto).placeholder(R.drawable.ic_no_profile_picture).into(binding.ivProfilePicture)
                Picasso.get().load(comment.backdropPath).placeholder(R.drawable.no_image).into(binding.rivMoviePoster)
                binding.vgComment.setOnClickListener {
                    onClickListenerComment(comment)
                }
            }
        }
    }
}