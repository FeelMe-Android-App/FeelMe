package com.feelme.feelmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.ListCategoriasBinding
import com.feelme.feelmeapp.features.home.model.Categorias


class CategoriasAdapter: RecyclerView.Adapter<CategoriasAdapter.MyviewHolder>() {

    private val listCategorias = mutableListOf<Categorias>()

    class MyviewHolder(private val binding: ListCategoriasBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(categorias: Categorias) {
            with(binding){
                tvCategoria.text = categorias.nome
                ivEmoji.setImageResource(categorias.icon)
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
        holder.bind(listCategorias[position])
    }

    override fun getItemCount(): Int = listCategorias.size

    fun setItensList(list: List<Categorias>) {
        listCategorias.clear()
        listCategorias.addAll(list)
    }
}