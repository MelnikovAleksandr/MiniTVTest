package ru.asmelnikov.minitvtest.data.di

import org.koin.dsl.module
import ru.asmelnikov.minitvtest.data.json.JsonReader

val dataModule = module {
    single<JsonReader> {
        JsonReader.Base(
            context = get()
        )
    }
}