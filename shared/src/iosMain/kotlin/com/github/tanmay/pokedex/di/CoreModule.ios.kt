package com.github.tanmay.pokedex.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<HttpClientEngine> {
        Darwin.create {
            configureRequest {
                setAllowsCellularAccess(true)
                setTimeoutInterval(30.0)
            }
        }
    }
}
