package com.feelme.feelmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.ListEmAltaBinding
import com.feelme.feelmeapp.models.Filmes
import com.feelme.feelmeapp.utils.OnClickListenerMovie

class EmAltaAdapter(
    private val listMovies: MutableList<Filmes>,
    private  val onClickListener: (movie: Filmes) -> Unit
): RecyclerView.Adapter<EmAltaAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ListEmAltaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            filmes: Filmes,
            onClickListener: (movie: Filmes) -> Unit
        ) {
            with(binding){
                tvNomeFilme.text = filmes.nome
                tvDataFilme.text = filmes.lancamento
                imageFilme.setImageResource(filmes.img)
                clMovieItem.setOnClickListener {
                    onClickListener(filmes)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ListEmAltaBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listMovies[position], onClickListener)
    }

    override fun getItemCount(): Int = listMovies.size

    fun setItensList(list: List<Filmes>) {
        listMovies.clear()
        listMovies.addAll(list)
    }
}
