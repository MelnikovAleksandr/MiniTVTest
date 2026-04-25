package ru.asmelnikov.minitvtest.data.di

import androidx.room.Room
import org.koin.dsl.module
import ru.asmelnikov.minitvtest.data.json.JsonReader
import ru.asmelnikov.minitvtest.data.local.MiniTvDatabase
import ru.asmelnikov.minitvtest.data.local.VideoReportDao
import ru.asmelnikov.minitvtest.data.repository.VideoRepositoryImpl
import ru.asmelnikov.minitvtest.domain.repository.VideoRepository

private const val DB_NAME = "minitv.db"

val dataModule = module {

    single<MiniTvDatabase> {
        Room.databaseBuilder(
            context = get(),
            klass = MiniTvDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single<VideoReportDao> {
        get<MiniTvDatabase>().videoReportDao()
    }

    single<JsonReader> {
        JsonReader.Base(
            context = get()
        )
    }

    single<VideoRepository> {
        VideoRepositoryImpl(
            videoReportDao = get(),
            jsonReader = get()
        )
    }
}