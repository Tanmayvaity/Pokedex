package com.github.tanmay.pokedex.core.domain.repository

import com.github.tanmay.pokedex.core.domain.model.PokemonSprite

interface PokemonRepository {
    suspend fun getPokemon() : Result<List<PokemonSprite>>
}