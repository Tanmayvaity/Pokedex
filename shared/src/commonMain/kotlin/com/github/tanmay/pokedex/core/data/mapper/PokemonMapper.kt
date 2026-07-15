package com.github.tanmay.pokedex.core.data.mapper

import com.github.tanmay.pokedex.core.data.dto.PokemonListItem
import com.github.tanmay.pokedex.core.domain.model.PokemonSprite

fun PokemonListItem.toDomain(): PokemonSprite {
    val id = url.trimEnd('/').substringAfterLast('/').toInt()
    return PokemonSprite(
        id = id,
        name = name.replaceFirstChar { it.uppercase() },
        spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    )
}