package com.feelme.feelmeapp.di

import com.feelme.feelmeapp.features.home.repository.HomeRepository
import com.feelme.feelmeapp.features.home.usecase.HomeUseCase
import com.feelme.feelmeapp.features.home.viewmodel.HomeViewModel
import com.feelme.feelmeapp.features.selectStream.repository.StreamListRepository
import com.feelme.feelmeapp.features.selectStream.usecase.StreamListUseCase
import com.feelme.feelmeapp.features.selectStream.viewmodel.StreamListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    //SelectStreaming
    single { StreamListRepository(context = get()) }
    single { StreamListUseCase(streamList = get()) }

    viewModel { StreamListViewModel(streamList = get()) }

    //Home
    single { HomeRepository(context = get()) }
    single { HomeUseCase(homeRepository = get()) }

    viewModel { HomeViewModel(homeUseCase = get()) }
}