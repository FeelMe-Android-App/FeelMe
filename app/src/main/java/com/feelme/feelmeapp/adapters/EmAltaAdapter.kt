package com.feelme.feelmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.ListEmAltaBinding
import com.feelme.feelmeapp.model.Result
import com.squareup.picasso.Picasso

class EmAltaAdapter(
    private val listMovies: List<Result>,
    private  val onClickListener: (movie: Result) -> Unit
): RecyclerView.Adapter<EmAltaAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ListEmAltaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            films: Result,
            onClickListener: (movie: Result) -> Unit
        ) {
            with(binding){
                tvNomeFilme.text = films.title
                Picasso.get().load(films.posterPath).into(imageFilme)
                clMovieItem.setOnClickListener {
                    onClickListener(films)
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
}
