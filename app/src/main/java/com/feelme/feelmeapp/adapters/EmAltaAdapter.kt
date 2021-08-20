package com.feelme.feelmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.ListEmAltaBinding
import com.feelme.feelmeapp.models.Filmes

class EmAltaAdapter: RecyclerView.Adapter<EmAltaAdapter.MyViewHolder>() {

   private val listMovies = mutableListOf<Filmes>()

    class MyViewHolder(private val binding: ListEmAltaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(filmes: Filmes) {
            with(binding){
                tvNomeFilme.text = filmes.nome
                tvDataFilme.text = filmes.lancamento
                imageFilme.setImageResource(filmes.img)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ListEmAltaBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int = listMovies.size

    fun setItensList(list: List<Filmes>) {
        listMovies.clear()
        listMovies.addAll(list)
    }
}
