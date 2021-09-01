package com.feelme.feelmeapp.features.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.EmojiItemBinding
import com.feelme.feelmeapp.features.dialog.usecase.EmojiList
import com.feelme.feelmeapp.features.dialog.view.Dialog

class EmojiListAdapter(private val emojiList: List<EmojiList>, private var dialog: Dialog): RecyclerView.Adapter<EmojiListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  {
        val binding = EmojiItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding, dialog)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(emojiList[position])
    }

    override fun getItemCount() = emojiList.count()

    class ViewHolder(val binding: EmojiItemBinding, private val dialog: Dialog): RecyclerView.ViewHolder(binding.root) {
        fun bind(emoji: EmojiList) {
            binding.ivEmoji.setImageResource(emoji.emoji)
            binding.tvEmojiFeeling.text = emoji.feeling
            this.itemView.setOnClickListener {
                dialog.dismiss()
                emoji.onClickListener()
            }
        }
    }
}