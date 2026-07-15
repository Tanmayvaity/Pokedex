package com.github.tanmay.pokedex.core.data.data_source

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


object KtorClientFactory {
    fun create(engine : HttpClientEngine) : HttpClient {
        return HttpClient(engine){
            expectSuccess = true
            install(ContentNegotiation){
                val json = Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
                json(json)
            }
            install(Logging){
                logger = Logger.SIMPLE
                level = LogLevel.HEADERS
            }
            install(HttpTimeout){
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis  = 15_000
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 3)
                exponentialDelay()
            }
            defaultRequest {
                url(BASE_URL)
                contentType(ContentType.Application.Json)
            }
        }

    }

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

}
