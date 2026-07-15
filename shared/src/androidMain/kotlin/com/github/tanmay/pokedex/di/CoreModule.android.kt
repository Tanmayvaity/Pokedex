package com.github.tanmay.pokedex.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

actual fun platformModule(): Module = module {
    single<HttpClientEngine> {
        OkHttp.create {
            config {
                retryOnConnectionFailure(true)
                connectTimeout(10, TimeUnit.SECONDS)
            }
        }
    }
}
