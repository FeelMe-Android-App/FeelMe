package com.feelme.feelmeapp.di

import com.feelme.feelmeapp.features.home.repository.HomeRepository
import com.feelme.feelmeapp.features.home.usecase.HomeUseCase
import com.feelme.feelmeapp.features.home.viewmodel.HomeViewModel
import com.feelme.feelmeapp.features.movieDetails.repository.MovieDetailsRepository
import com.feelme.feelmeapp.features.movieDetails.usecase.MovieDetailsUseCase
import com.feelme.feelmeapp.features.movieDetails.viewmodel.MovieDetailsViewModel
import com.feelme.feelmeapp.features.selectStream.repository.StreamListRepository
import com.feelme.feelmeapp.features.selectStream.usecase.StreamListUseCase
import com.feelme.feelmeapp.features.selectStream.viewmodel.StreamListViewModel
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

    viewModel { MovieDetailsViewModel(movieDetailsUseCase = get()) }
}