package com.feelme.feelmeapp.features.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.ProfileActivity
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.adapters.CategoriesAdapter
import com.feelme.feelmeapp.adapters.EmAltaAdapter
import com.feelme.feelmeapp.databinding.FragmentHomeBinding
import com.feelme.feelmeapp.features.dialog.usecase.ButtonStyle
import com.feelme.feelmeapp.features.dialog.usecase.DialogData
import com.feelme.feelmeapp.features.dialog.view.Dialog
import com.feelme.feelmeapp.features.genre.view.GenreActivity
import com.feelme.feelmeapp.features.home.viewmodel.HomeViewModel
import com.feelme.feelmeapp.globalLiveData.UserProfile
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { FragmentHome ->
            FragmentHome.vgHomeFragment.isVisible = false
            FragmentHome.ivFotoLogin.setOnClickListener {
                showLoginFacebookDialog()
            }
        }

        setupRequests()
        setupObservables()
    }

    private fun setupRequests() {
        viewModel.command = MutableLiveData()
        viewModel.getGenres()
        viewModel.getNowPlayingMovies()
        viewModel.getLastRelease()
    }

    private fun setupObservables() {
        activity?.let { FragmentActivity ->
            viewModel.onSuccessNowPlaying.observe(FragmentActivity, {
                binding?.let { FragmentHome ->
                    FragmentHome.rvEmAlta.adapter = EmAltaAdapter(it) { Result ->
                        val intent = Intent(context, MovieDetailsActivity::class.java)
                        intent.putExtra(EXTRA_MOVIE_ID, Result.id)
                        startActivity(intent)
                    }
                    FragmentHome.rvEmAlta.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                    FragmentHome.rvEmAlta.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    FragmentHome.vgLoader.vgLoader.isVisible = false
                    FragmentHome.vgHomeFragment.isVisible = true
                }
            })

            viewModel.onSuccessLastRelease.observe(FragmentActivity, {
                binding?.let { FragmentHome ->
                    FragmentHome.rvLancamentos.adapter = EmAltaAdapter(it) { Result ->
                        val intent = Intent(context, MovieDetailsActivity::class.java)
                        intent.putExtra(EXTRA_MOVIE_ID, Result.id)
                        startActivity(intent)
                    }
                    FragmentHome.rvLancamentos.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                    FragmentHome.rvLancamentos.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    FragmentHome.rvLancamentos.isVisible = true
                    FragmentHome.tvLancamentos.isVisible = true
                }
            })

            viewModel.onSucessGenres.observe(FragmentActivity, { GenreList ->
                binding?.let { FragmentHome ->
                    FragmentHome.rvCategoria.adapter = CategoriesAdapter(GenreList) {
                        val intent = Intent(context, GenreActivity::class.java)
                        intent.putExtra(EXTRA_CATEGORY_ID, it.id)
                        intent.putExtra(EXTRA_CATEGORY_NAME, it.name)
                        startActivity(intent)
                    }
                    FragmentHome.rvCategoria.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                }
            })

            UserProfile.currentUser.observe(FragmentActivity, { CurrentUser ->
                CurrentUser?.let {
                    binding?.let { FragmentHome ->
                        if(it.logged) {
                            val homeText = "Olá, ${it.displayName}"
                            Picasso.get().load(it.photoUrl).placeholder(R.drawable.ic_no_profile_picture).into(FragmentHome.ivFotoLogin)
                            FragmentHome.tvNomeLogin.text = homeText
                            Log.i("firebaseToken", it.token.toString())

                            FragmentHome.ivFotoLogin.setOnClickListener {
                                startActivity(Intent(context, ProfileActivity::class.java))
                            }
                        }
                    }
                }
            })
        }
    }

    private fun showLoginFacebookDialog() {
        Dialog(
            DialogData(
                title = "Entre",
                subtitle = "Faça login com seu Facebook para acessar esse e outros recursos.",
                image = R.drawable.ic_signup,
                button = ButtonStyle("Logar com Facebook",R.drawable.ic_facebook,R.color.facebook_bt) {
                    Log.i("ButtonAction","Teste de Ação Personalizada")
                }
            )
        ).show(parentFragmentManager, "LoginDialog")
    }

    companion object {
        const val EXTRA_MOVIE_ID = "movieId"
        const val EXTRA_CATEGORY_ID = "categoryId"
        const val EXTRA_CATEGORY_NAME = "categoryName"
    }
}