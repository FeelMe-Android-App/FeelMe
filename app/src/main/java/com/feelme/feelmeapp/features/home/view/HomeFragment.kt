package com.feelme.feelmeapp.features.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.adapters.CategoriasAdapter
import com.feelme.feelmeapp.adapters.EmAltaAdapter
import com.feelme.feelmeapp.databinding.FragmentHomeBinding
import com.feelme.feelmeapp.features.dialog.view.Dialog
import com.feelme.feelmeapp.models.Categorias
import com.feelme.feelmeapp.models.Filmes


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var adapterEmAlta = EmAltaAdapter()
    private var adapterCategorias = CategoriasAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.ivFotoLogin.setOnClickListener {
//            Dialog("Example","new Example").show(parentFragmentManager, "CustomDialog")
//        }

        setFilmes()
        setCategorias()
        setRecyclerViewFilmes()
        setRecyclerViewCategorias()
    }

    private fun setRecyclerViewFilmes() {
        binding.rvEmAlta.adapter = adapterEmAlta
        binding.rvEmAlta.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    }

    private fun setRecyclerViewCategorias() {
        binding.rvCategoria.adapter = adapterCategorias
        binding.rvCategoria.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    }

    private fun setFilmes() {
        adapterEmAlta.setItensList(
            arrayListOf(
                Filmes(
                    1, "The Revenant", "Jan 31,2015", R.drawable.the_revenant
                ),
                Filmes(
                    2, "No country for old men", "Mar 02,2007", R.drawable.no_country_for_old_men
                ),
                Filmes(
                    3, "The Fight Clube", "Nov 09,1999", R.drawable.the_fight_club
                ),
                Filmes(
                    4, "There will be blood", "Aug 17,2007", R.drawable.there_will_be_blood
                ),
                Filmes(
                    5, "Trainspotting", "Fev 11,1996", R.drawable.trainspotting
                ),
                Filmes(
                    6, "Tene", "Dec 24,2020", R.drawable.tene
                ),
            )
        )
    }

    private fun setCategorias() {
        adapterCategorias.setItensList(
            arrayListOf(
                Categorias(
                    1, "Ação"
                ),
                Categorias(
                    2, "Drama"
                ),
                Categorias(
                    3, "Comédia"
                ),
                Categorias(
                    4, "Romance"
                ),
                Categorias(
                    5, "Ficção"
                ),
                Categorias(
                    6, "Estrangeiros"
                ),
            )
        )
    }

}