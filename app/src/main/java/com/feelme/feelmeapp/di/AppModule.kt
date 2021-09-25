package com.feelme.feelmeapp.di

import com.feelme.feelmeapp.features.selectStream.repository.StreamListRepository
import com.feelme.feelmeapp.features.selectStream.usecase.StreamListUseCase
import com.feelme.feelmeapp.features.selectStream.viewmodel.StreamListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single {
        StreamListRepository(get())
        StreamListUseCase(streamList = get())
    }

    viewModel {
        StreamListViewModel(streamList = get())
    }
}