package com.github.tanmay.pokedex

import android.app.Application
import com.github.tanmay.pokedex.di.initKoin

class PokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}