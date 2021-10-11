package com.feelme.feelmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.ListCategoriasBinding
import com.feelme.feelmeapp.model.Genre


class CategoriesAdapter(
    private val listCategories: List<Genre> = listOf(),
    private val onClickListener: (category: Genre) -> Unit
): RecyclerView.Adapter<CategoriesAdapter.MyviewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val view = ListCategoriasBinding.inflate(LayoutInflater.from(parent.context))
        return MyviewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(listCategories[position])
    }

    override fun getItemCount(): Int = listCategories.count()

    class MyviewHolder(
        private val binding: ListCategoriasBinding,
        private val onClickListener: (category: Genre) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(categorias: Genre) {
            with(binding){
                tvCategory.text = categorias.name

                vgCategory.setOnClickListener {
                    onClickListener(categorias)
                }
            }
        }

    }
}