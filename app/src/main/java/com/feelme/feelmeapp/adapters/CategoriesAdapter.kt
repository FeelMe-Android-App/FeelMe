package com.feelme.feelmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.ListCategoriasBinding
import com.feelme.feelmeapp.model.Genre


class CategoriesAdapter(
    private val listCategories: List<Genre> = listOf()
): RecyclerView.Adapter<CategoriesAdapter.MyviewHolder>() {

    class MyviewHolder(private val binding: ListCategoriasBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(categorias: Genre) {
            with(binding){
                tvCategoria.text = categorias.name
//                ivEmoji.setImageResource(categorias.icon)
//                tvCategoria.setOnClickListener {
//                    Dialog("exemplo","outro Exemplo").show((it.context as FragmentActivity).supportFragmentManager, "customDialog")
//                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val view = ListCategoriasBinding.inflate(LayoutInflater.from(parent.context))
        return MyviewHolder(view)
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(listCategories[position])
    }

    override fun getItemCount(): Int = listCategories.size
}