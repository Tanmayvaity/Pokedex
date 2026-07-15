package com.github.tanmay.pokedex.di

import com.github.tanmay.pokedex.core.data.data_source.KtorClientFactory
import com.github.tanmay.pokedex.core.data.repository.PokemonRepositoryImpl
import com.github.tanmay.pokedex.core.domain.repository.PokemonRepository
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


// HttpClientEngine (platform specific)
expect fun platformModule(): Module

// HttpClient
val networkModule = module {
    single<HttpClient> {
        KtorClientFactory.create(get())
    }
}

// Repositories shared across features
val dataModule = module {
    singleOf(::PokemonRepositoryImpl) bind PokemonRepository::class
}
