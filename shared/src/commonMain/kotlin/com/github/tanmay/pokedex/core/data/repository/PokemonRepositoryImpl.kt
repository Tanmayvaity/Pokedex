package com.github.tanmay.pokedex.core.data.repository

import com.github.tanmay.pokedex.core.data.dto.PokemonListResponse
import com.github.tanmay.pokedex.core.data.mapper.toDomain
import com.github.tanmay.pokedex.core.domain.model.PokemonSprite
import com.github.tanmay.pokedex.core.domain.repository.PokemonRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments


class PokemonRepositoryImpl(
    private val client: HttpClient
) : PokemonRepository {


    override suspend fun getPokemon(): Result<List<PokemonSprite>> {
        return runCatching {
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                appendPathSegments("pokemon")
            }
        }.body<PokemonListResponse>().results.map { pokemon -> pokemon.toDomain() }}.fold(
            onSuccess = { sprites ->
                Result.success(sprites)
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }
}