package com.feelme.feelmeapp.di

import com.feelme.feelmeapp.features.dialog.repository.DialogRepository
import com.feelme.feelmeapp.features.dialog.usecase.DialogUseCase
import com.feelme.feelmeapp.features.dialog.viewmodel.DialogViewModel
import com.feelme.feelmeapp.features.genre.repository.GenreRepository
import com.feelme.feelmeapp.features.genre.usecase.GenreUseCase
import com.feelme.feelmeapp.features.genre.viewmodel.GenreViewModel
import com.feelme.feelmeapp.features.home.repository.HomeRepository
import com.feelme.feelmeapp.features.home.usecase.HomeUseCase
import com.feelme.feelmeapp.features.home.viewmodel.HomeViewModel
import com.feelme.feelmeapp.features.movieDetails.repository.MovieDetailsRepository
import com.feelme.feelmeapp.features.movieDetails.usecase.MovieDetailsUseCase
import com.feelme.feelmeapp.features.movieDetails.viewmodel.MovieDetailsViewModel
import com.feelme.feelmeapp.features.profile.repository.ProfileRepository
import com.feelme.feelmeapp.features.profile.usecase.ProfileUseCase
import com.feelme.feelmeapp.features.profile.viewmodel.ProfileViewModel
import com.feelme.feelmeapp.features.savedMovies.repository.SavedMoviesRepository
import com.feelme.feelmeapp.features.savedMovies.usecase.SavedMoviesUseCase
import com.feelme.feelmeapp.features.savedMovies.viewmodel.SavedMoviesViewModel
import com.feelme.feelmeapp.features.search.repository.SearchRepository
import com.feelme.feelmeapp.features.search.usecase.SearchUseCase
import com.feelme.feelmeapp.features.search.viewmodel.SearchViewModel
import com.feelme.feelmeapp.features.selectStream.repository.StreamListRepository
import com.feelme.feelmeapp.features.selectStream.usecase.StreamListUseCase
import com.feelme.feelmeapp.features.selectStream.viewmodel.StreamListViewModel
import com.feelme.feelmeapp.features.watchedMovies.repository.WatchedMoviesRepository
import com.feelme.feelmeapp.features.watchedMovies.usecase.WatchedMoviesUseCase
import com.feelme.feelmeapp.features.watchedMovies.viewmodel.WatchedMoviesModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    //SelectStreaming
    single { StreamListRepository(context = get()) }
    single { StreamListUseCase(streamListRepository = get()) }

    viewModel { StreamListViewModel(streamListUseCase = get()) }

    //Home
    single { HomeRepository(context = get()) }
    single { HomeUseCase(homeRepository = get()) }

    viewModel { HomeViewModel(homeUseCase = get()) }

    //MovieDetails
    single { MovieDetailsRepository(context = get()) }
    single { MovieDetailsUseCase(movieDetailsRepository = get()) }

    viewModel { MovieDetailsViewModel(movieDetailsUseCase = get(), context = get()) }

    //Search
    single { SearchRepository(get()) }
    single { SearchUseCase(searchRepository = get()) }

    viewModel { SearchViewModel(searchUseCase = get(), searchRepository = get()) }

    //Genre
    single { GenreRepository(context = get()) }
    single { GenreUseCase() }

    viewModel { GenreViewModel(genreUseCase = get(), genreRepository = get()) }

    //Dialog
    single { DialogRepository(context = get()) }
    single { DialogUseCase(dialogRepository = get()) }

    viewModel { DialogViewModel(dialogUseCase = get()) }

    //UnWatchedMovies
    single { SavedMoviesRepository() }
    single { SavedMoviesUseCase(get()) }

    viewModel { SavedMoviesViewModel(savedMoviesRepository = get(), savedMoviesUseCase = get()) }

    //WatchedMovies
    single { WatchedMoviesRepository() }
    single { WatchedMoviesUseCase() }

    viewModel { WatchedMoviesModel(watchedMoviesRepository = get(), watchedMoviesUseCase = get()) }

    //Profile
    single { ProfileRepository() }
    single { ProfileUseCase(profileRepository = get()) }

    viewModel { ProfileViewModel(profileUseCase = get()) }
}