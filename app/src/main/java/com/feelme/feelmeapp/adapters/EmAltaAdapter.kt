package com.feelme.feelmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.ListEmAltaBinding
import com.feelme.feelmeapp.features.home.usecase.Filmes
import com.feelme.feelmeapp.model.NowPlaying
import com.feelme.feelmeapp.model.Result
import com.squareup.picasso.Picasso

class EmAltaAdapter(
    private val listMovies: List<Result>,
    private  val onClickListener: (movie: Result) -> Unit
): RecyclerView.Adapter<EmAltaAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ListEmAltaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            filmes: Result,
            onClickListener: (movie: Result) -> Unit
        ) {
            with(binding){
                tvNomeFilme.text = filmes.title
                tvDataFilme.text = filmes.release_date
                Picasso.get().load(filmes.poster_path).into(imageFilme)
//                imageFilme.setImageResource()
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
}
