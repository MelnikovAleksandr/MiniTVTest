package ru.asmelnikov.minitvtest.presentation.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.asmelnikov.minitvtest.presentation.viewmodel.VideoViewModel

val uiModule = module {
    viewModel {
        VideoViewModel(
            repository = get(),
            stringResourceProvider = get()
        )
    }
}