package com.github.tanmay.pokedex.di

import com.github.tanmay.pokedex.feature.feature_feed.di.feedModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration


fun initKoin(config : KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    modules(
        platformModule(), // HttpClientEngine
        networkModule,    // HttpClient
        dataModule,       // Repositories
        feedModule        // feature_feed
    )
}