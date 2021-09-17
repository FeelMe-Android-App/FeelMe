package com.feelme.feelmeapp.features.movieDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.TextCategItemBinding
import com.feelme.feelmeapp.model.Genre

class MovieCategoriesAdapter(
    private val categories: List<Genre>,
    private val onClickListenerCategories: (categories: Genre) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = TextCategItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(categories[position], onClickListenerCategories)
    }

    override fun getItemCount() = categories.count()

    class ViewHolder(
        val binding: TextCategItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            category: Genre,
            onClickListenerCategories: (categories: Genre) -> Unit
        ) {
            binding.tvMovieCategory.text = category.name
            binding.tvMovieCategory.setOnClickListener { onClickListenerCategories(category) }
        }
    }
}