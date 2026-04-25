package ru.asmelnikov.minitvtest

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.asmelnikov.minitvtest.data.di.dataModule
import ru.asmelnikov.minitvtest.presentation.di.uiModule
import ru.asmelnikov.minitvtest.utils.utilsModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, uiModule, utilsModule)
        }
    }

}